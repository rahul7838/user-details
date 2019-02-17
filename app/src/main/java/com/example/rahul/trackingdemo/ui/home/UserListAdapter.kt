//package com.example.rahul.trackingdemo.ui.home
//
//import android.net.Uri
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.example.rahul.trackingdemo.R
//import com.example.rahul.trackingdemo.data.model.Result
//import com.facebook.drawee.backends.pipeline.Fresco
//import com.facebook.drawee.interfaces.DraweeController
//import com.facebook.drawee.view.DraweeView
//import com.facebook.imagepipeline.request.ImageRequest
//
//class UserListAdapter : RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {
//
//
//    var list: MutableList<Result> = ArrayList()
//
//    var onItemClick: ((Result) -> Unit)? = null
//
//    fun prepareNewsList(result: List<Result>) {
//        list.clear()
//        result?.let { list.addAll(it) }
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
//        val view = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)
//        return UserListViewHolder(view)
//    }
//
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
//        var dob = list[position].dob.date
//        dob = dob.substring(0,9)
//        dob = dob.replace("-","/")
//
//        var firstname = list[position].name.first
//        firstname = firstname[0].toUpperCase().toString()+firstname.substring(1)
//        var lastName = list[position].name.last
//        lastName = lastName[0].toUpperCase().toString() + lastName.substring(1)
//        val name = "$firstname $lastName"
//
//        var number = list[position].phone
//        val re = Regex("[^0-9]")
//        number = re.replace(number, "")
//        if(number.length >= 10) {
//            number = "${number.substring(0, 3)} ${number.substring(3, 6)} ${number.substring(7, 9)}XX"
//        }
//
//        holder?.txtName?.text = name
//        holder?.txtMobile?.text = number
//        holder?.txtEmail?.text = list[position].email
//        holder?.txtBirthDate?.text = dob
//
//        var draweeController: DraweeController?= null
//        val pipelineDraweeControllerBuilder = Fresco.newDraweeControllerBuilder()
////        if(list[position].picture){
//            draweeController = pipelineDraweeControllerBuilder.setImageRequest(ImageRequest.fromUri(Uri.parse(list[position].picture.thumbnail)))
//                    .setOldController(holder?.draweeView?.controller)
//                    .build()
//        holder?.draweeView?.controller = draweeController
//    }
//
//    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val txtName: TextView? = itemView.findViewById(R.id.title) as TextView?
//        val txtMobile: TextView? = itemView.findViewById(R.id.mobile_numer)
//        val txtBirthDate: TextView? = itemView.findViewById(R.id.birth_date)
//        val txtEmail: TextView? = itemView.findViewById(R.id.email)
//        val draweeView: DraweeView<*> = itemView.findViewById(R.id.item_image) as DraweeView<*>
//
//        init {
//            itemView.setOnClickListener { onItemClick?.invoke(list[adapterPosition]) }
//        }
//    }
//}