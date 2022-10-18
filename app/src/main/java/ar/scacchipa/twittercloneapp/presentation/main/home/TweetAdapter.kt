package ar.scacchipa.twittercloneapp.presentation.main.home

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.scacchipa.twittercloneapp.R
import ar.scacchipa.twittercloneapp.databinding.LayoutCardTweetBinding
import ar.scacchipa.twittercloneapp.domain.model.ReferencedType
import ar.scacchipa.twittercloneapp.domain.model.TweetInfo

class TweetAdapter(
    private val tweets: List<TweetInfo>
) : RecyclerView.Adapter<TweetAdapter.ViewHolder>() {

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = LayoutCardTweetBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_card_tweet, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet = tweets[position]
        holder.binding.apply {
            txtTweet.text = tweet.text
            textviewCommentCount.text = tweet.publicMetrics.replyCount.toString()
            textviewRetweetCount.text = tweet.publicMetrics.retweetCount.toString()
            textViewLikeCount.text = tweet.publicMetrics.likeCount.toString()
            imgProfile.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    tweet.profileBitmapByteArray,
                    0,
                    tweet.profileBitmapByteArray?.size ?: 0
                )
            )
            txtOwner.text = tweet.name
            imgTick.visibility = if (tweet.verified) View.VISIBLE else View.GONE
            txtCreationInfo.text = tweet.username
            txtReference.text = when (tweet.referenceTweet) {
                is ReferencedType.RetweetedType -> "${tweet.referenceTweet.name} Retweeted"
                is ReferencedType.RepliedToType -> "Replied to ${tweet.referenceTweet.name}"
                is ReferencedType.QuotedType -> "Quoted by ${tweet.referenceTweet.name}"
                is ReferencedType.NoReferencedType -> ""
            }
            txtReference.visibility = when (tweet.referenceTweet) {
                is ReferencedType.NoReferencedType -> View.GONE
                else -> View.VISIBLE
            }
            icReferenceType.setImageResource(when (tweet.referenceTweet) {
                    is ReferencedType.RetweetedType -> R.drawable.ic_tweet_retweet
                    is ReferencedType.RepliedToType -> R.drawable.ic_tweet_msg
                    is ReferencedType.QuotedType -> R.drawable.ic_tweet_msg
                    is ReferencedType.NoReferencedType -> 0
                }
            )

            icReferenceType.visibility = when (tweet.referenceTweet) {
                is ReferencedType.RetweetedType -> View.VISIBLE
                is ReferencedType.RepliedToType -> View.VISIBLE
                is ReferencedType.QuotedType -> View.GONE
                is ReferencedType.NoReferencedType -> View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return tweets.size
    }
}