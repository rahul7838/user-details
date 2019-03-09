package com.example.rahul.trackingdemo.ui.home

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rahul.trackingdemo.R
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.home.HomeActivity.Companion.diff
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.DraweeView
import com.facebook.imagepipeline.request.ImageRequest


class UserListAdapter(val context: Context) : PagedListAdapter<Result, UserListAdapter.UserListViewHolder>(diff) {



    var onItemClick: ((Result) -> Unit)? = null

    fun prepareNewsList(result: List<Result>) {
//        list.clear()
//        result.let { list.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {

        holder?.txtName?.text = getItem(position)?.name?.first
        holder?.txtMobile?.text = getItem(position)?.phone
        holder?.txtEmail?.text = getItem(position)?.email
        holder?.txtBirthDate?.text = getItem(position)?.dob?.date

        val draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(ImageRequest.fromUri(Uri.parse(getItem(position)?.picture?.medium)))
                .setOldController(holder?.draweeView?.controller)
                .build()
        val roundingParams = RoundingParams()
        roundingParams.roundAsCircle = true
        val hierarchy = GenericDraweeHierarchyBuilder(context.resources).setRoundingParams(roundingParams)

        holder?.draweeView?.controller = draweeController
        holder?.draweeView?.hierarchy = hierarchy.build()
    }

    override fun getItemCount(): Int {
        Log.i("UserListAdapter", super.getItemCount().toString())
        return super.getItemCount()
    }

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView? = itemView.findViewById(com.example.rahul.trackingdemo.R.id.title) as TextView?
        val txtMobile: TextView? = itemView.findViewById(R.id.mobile_numer)
        val txtBirthDate: TextView? = itemView.findViewById(R.id.birth_date)
        val txtEmail: TextView? = itemView.findViewById(R.id.email)
        val draweeView: DraweeView<*> = itemView.findViewById(R.id.item_image) as DraweeView<*>

        init {
//            itemView.setOnClickListener { onItemClick?.invoke(list[adapterPosition]) }
        }
    }
}