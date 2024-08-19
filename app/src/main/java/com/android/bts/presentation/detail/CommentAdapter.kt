package com.android.bts.presentation.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.bts.R
import com.android.bts.data.remote.CommentItem

class CommentAdapter(private var comments: List<CommentItem>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val commentTextView: TextView = itemView.findViewById(R.id.commentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.authorTextView.text = comment.snippet.topLevelComment.snippet.authorDisplayName
        holder.commentTextView.text = comment.snippet.topLevelComment.snippet.textDisplay

        Log.d("CommentAdapter", "Binding comment at position $position: ${comment.snippet.topLevelComment.snippet.textDisplay}")
    }

    override fun getItemCount(): Int = comments.size

    fun updateComments(newComments: List<CommentItem>) {
        comments = newComments
        Log.d("CommentAdapter", "Updated comments list: $comments")
        notifyDataSetChanged()  // RecyclerView를 갱신합니다.
    }
}
