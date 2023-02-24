package com.example.login.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.ItemRecyclerviewRoomBinding
import com.example.login.network.retrofit.response.FindRoomResponse

class RoomAdapter(private val dataList: List<FindRoomResponse>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RoomViewHolder(ItemRecyclerviewRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val data = dataList[position]
        val binding = (holder as RoomViewHolder).binding
        //뷰에 데이터 출력
        binding.roomName.text = data.name
        Log.d("상태", data.name)
//        binding.root.setOnClickListener {
//            val messageFragment = ChatFragment()
//
//            val fragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
//            fragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainer, messageFragment)
//                .addToBackStack(null)
//                .commit()
//        }
    }
}

class RoomViewHolder(val binding: ItemRecyclerviewRoomBinding): RecyclerView.ViewHolder(binding.root)

