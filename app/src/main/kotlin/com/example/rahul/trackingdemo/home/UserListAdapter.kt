package com.example.rahul.trackingdemo.home

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rahul.trackingdemo.R
import com.example.rahul.trackingdemo.data.model.NetworkState
import com.example.rahul.trackingdemo.data.model.Result
import com.example.rahul.trackingdemo.data.model.Status
import com.example.rahul.trackingdemo.home.HomeActivity.Companion.diff
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.DraweeView
import com.facebook.imagepipeline.request.ImageRequest
import com.google.android.material.snackbar.Snackbar
import java.security.AccessControlContext


class UserListAdapter(val context: Context) : PagedListAdapter<Result, RecyclerView.ViewHolder>(diff) {
  private var networkState: NetworkState? = null
  var onItemClick: ((Int) -> Unit)? = null
  private val TAG = UserListAdapter::class.java.canonicalName


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val layoutInflater = LayoutInflater.from(parent?.context)
    val networkInflater = layoutInflater.inflate(R.layout.progress_bar, parent, false)
    return when (viewType) {
      R.layout.progress_bar -> NetworkStatusViewHolder(networkInflater)
      R.layout.list_item -> UserListViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
      else -> throw IllegalArgumentException("viewType $viewType")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    if (holder is NetworkStatusViewHolder) {
      holder.bindTo(networkState)
    } else {
      holder as UserListViewHolder
      holder?.txtName?.text = getItem(position)?.name?.first
      val s: CharSequence = getItem(position)?.phone as CharSequence
      holder?.txtMobile?.text = s
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

  }

  override fun getItemCount(): Int {
//        Log.i("UserListAdapter", super.getItemCount().toString())
    return super.getItemCount() + if (hasExtraRow()) 1 else 0
  }

  override fun getItemViewType(position: Int): Int {
    super.getItemViewType(position)
    return if (networkState == NetworkState.LOADING && position == itemCount - 1) {
      R.layout.progress_bar
    } else {
      R.layout.list_item
    }
  }

  private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED


  fun setNetwork(newNetworkState: NetworkState) {
    val previousState = this.networkState
    val hadExtraRow = hasExtraRow()
    this.networkState = newNetworkState
    val hasExtraRow = hasExtraRow()
    if (hadExtraRow != hasExtraRow) {
      if (hadExtraRow) {
        Log.i(TAG, "itemRemoved")
        accessibilityEvent("loading completed")
        notifyItemRemoved(super.getItemCount())
      } else {
        Log.i(TAG, "itemInserted")
        accessibilityEvent("loading data")
        notifyItemInserted(super.getItemCount())
      }
    } else if (hasExtraRow && previousState != newNetworkState) {
      Log.i(TAG, "itemChanged")
      notifyItemChanged(itemCount - 1)
    }
  }

  private fun accessibilityEvent(text: String) {
    val manager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    if(manager.isEnabled){
      val e = AccessibilityEvent.obtain()
      e.eventType = AccessibilityEvent.TYPE_ANNOUNCEMENT
      Log.i(TAG, javaClass.name)
      e.className = javaClass.name
      e.packageName = context.packageName
      e.text.add(text)
      manager.sendAccessibilityEvent(e)
    }
  }

  inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val txtName: TextView? = itemView.findViewById(com.example.rahul.trackingdemo.R.id.title) as TextView?
    val txtMobile: TextView? = itemView.findViewById(R.id.mobile_numer)
    val txtBirthDate: TextView? = itemView.findViewById(R.id.birth_date)
    val txtEmail: TextView? = itemView.findViewById(R.id.email)
    val draweeView: DraweeView<*> = itemView.findViewById(R.id.item_image) as DraweeView<*>

    init {
      itemView.setOnClickListener { onItemClick?.invoke(adapterPosition) }
    }
  }

  inner class NetworkStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar_id)

    fun bindTo(network: NetworkState?) {
      progressBar.visibility = toVisibility(network?.status == Status.RUNNING)
    }

    private fun toVisibility(boolean: Boolean): Int {
      return if (boolean) {
        View.VISIBLE
      } else {
        View.INVISIBLE
      }

    }
  }
}