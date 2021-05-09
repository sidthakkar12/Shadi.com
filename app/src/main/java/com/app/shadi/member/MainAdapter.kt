package com.app.shadi.member

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.app.shadi.R
import com.app.shadi.database.entity.UserEntity
import com.app.shadi.listener.OnClickListener
import com.app.shadi.utility.extension.loadImage
import kotlinx.android.synthetic.main.item_main.view.*
import kotlinx.android.synthetic.main.item_main_two.view.*


class MainAdapter(private val users: ArrayList<UserEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var onClickListener: OnClickListener
    private var orientation = ORIENTATION_VERTICAL

    companion object {
        const val ORIENTATION_VERTICAL = 1
        const val ORIENTATION_HORIZONTAL = 2
    }

    class VerticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserEntity) {
            itemView.apply {
                ivProfile.loadImage(user.profilePicture)
                tvName.text = context.getString(R.string.name_s_s_s, user.title, user.firstName, user.lastName)
                tvAge.text = context.getString(R.string.age_s, user.age)
                tvLocation.text = context.getString(R.string.location_s_s_s, user.city, user.state, user.country)

                tvMessage.text = if (user.isAccepted) context.getString(R.string.member_accepted) else context.getString(R.string.member_declined)
                tvMessage.setTextColor(if (user.isAccepted) ContextCompat.getColor(context, R.color.color_28EE00) else ContextCompat.getColor(context, R.color.color_EE0021))

                ivAccept.isVisible = !user.isAccepted && !user.isDeclined
                ivDecline.isVisible = !user.isAccepted && !user.isDeclined
                tvMessage.isVisible = user.isAccepted || user.isDeclined
            }
        }
    }

    class HorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: UserEntity) {
            itemView.apply {
                ivProfileTwo.loadImage(user.profilePicture)
                tvNameTwo.text = context.getString(R.string.name_s_s_s, user.title, user.firstName, user.lastName)
                tvEmailTwo.text = context.getString(R.string.email_s, user.email)
                tvPhoneTwo.text = context.getString(R.string.phone_s, user.phone)
                tvAgeTwo.text = context.getString(R.string.age_s, user.age)
                tvLocationTwo.text = context.getString(R.string.location_s_s_s, user.city, user.state, user.country)

                tvMessageTwo.text = if (user.isAccepted) context.getString(R.string.member_accepted) else context.getString(R.string.member_declined)
                tvMessageTwo.setTextColor(if (user.isAccepted) ContextCompat.getColor(context, R.color.color_28EE00) else ContextCompat.getColor(context, R.color.color_EE0021))

                btnAccept.isVisible = !user.isAccepted && !user.isDeclined
                btnDecline.isVisible = !user.isAccepted && !user.isDeclined
                tvMessageTwo.isVisible = user.isAccepted || user.isDeclined
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ORIENTATION_VERTICAL -> VerticalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false))
            else -> HorizontalViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_two, parent, false))
        }
    }

    override fun getItemCount(): Int = users.size

    override fun getItemViewType(position: Int): Int {
        return orientation
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            ORIENTATION_VERTICAL -> {
                val viewHolder = holder as VerticalViewHolder
                viewHolder.bind(users[position])

                viewHolder.itemView.ivAccept.setOnClickListener {
                    onClickListener.onClick(users[position].phone, status = UserEntity.STATUS_IS_ACCEPTED)
                }

                viewHolder.itemView.ivDecline.setOnClickListener {
                    onClickListener.onClick(users[position].phone, status = UserEntity.STATUS_IS_DECLINED)
                }
            }

            ORIENTATION_HORIZONTAL -> {
                val viewHolder = holder as HorizontalViewHolder
                viewHolder.bind(users[position])

                viewHolder.itemView.btnAccept.setOnClickListener {
                    onClickListener.onClick(users[position].phone, status = UserEntity.STATUS_IS_ACCEPTED)
                }

                viewHolder.itemView.btnDecline.setOnClickListener {
                    onClickListener.onClick(users[position].phone, status = UserEntity.STATUS_IS_DECLINED)
                }
            }
        }
    }

    fun addUsers(users: List<UserEntity>) {
        this.users.clear()
        this.users.addAll(users)
    }

    fun updateUser(userEntity: UserEntity) {
        val user = users.find { it.phone == userEntity.phone }
        val position = users.indexOf(user)
        users[position] = userEntity
        notifyItemChanged(position)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setOrientation(orientation: Int) {
        this.orientation = orientation
    }
}