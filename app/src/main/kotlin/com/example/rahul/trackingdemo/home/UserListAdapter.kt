package com.example.rahul.trackingdemo.home

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.rahul.trackingdemo.data.model.Result
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.DraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.drawee.generic.RoundingParams
import android.content.Context
import com.example.rahul.trackingdemo.R
import com.example.rahul.trackingdemo.utils.FormatUtility
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder


class UserListAdapter(val context: Context) : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {


    var list: MutableList<Result> = ArrayList()

    var onItemClick: ((Result) -> Unit)? = null

    fun prepareNewsList(result: List<Result>) {
        list.clear()
        result.let { list.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        return UserListViewHolder(view)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {

        val name = FormatUtility.formatName(list[position].name)
        val number = FormatUtility.formatPhoneNUmber(list[position].phone)
        val reverseDOb = FormatUtility.formatDob(list[position].dob.date)
        holder?.txtName?.text = name
        holder?.txtMobile?.text = number
        holder?.txtEmail?.text = list[position].email
        holder?.txtBirthDate?.text = reverseDOb

        val draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(list[position].picture.medium)))
                .setOldController(holder?.draweeView?.controller)
                .build()
        val roundingParams = RoundingParams()
        roundingParams.roundAsCircle = true
        val hierarchy = GenericDraweeHierarchyBuilder(context.resources).setRoundingParams(roundingParams)

        holder?.draweeView?.controller = draweeController
        holder?.draweeView?.hierarchy = hierarchy.build()
    }

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView? = itemView.findViewById(com.example.rahul.trackingdemo.R.id.title) as TextView?
        val txtMobile: TextView? = itemView.findViewById(R.id.mobile_numer)
        val txtBirthDate: TextView? = itemView.findViewById(R.id.birth_date)
        val txtEmail: TextView? = itemView.findViewById(R.id.email)
        val draweeView: DraweeView<*> = itemView.findViewById(R.id.item_image) as DraweeView<*>

        init {
            itemView.setOnClickListener { onItemClick?.invoke(list[adapterPosition]) }
        }
    }
}