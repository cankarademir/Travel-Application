package com.cankarademir.travelapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cankarademir.travelapplication.adapter.RecyclerViewAdapter
import com.cankarademir.travelapplication.databinding.FragmentGalleryBinding
import com.cankarademir.travelapplication.models.Travel
import com.cankarademir.travelapplication.models.TravelData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class GalleryFragment : Fragment() {

    private lateinit var _binding: FragmentGalleryBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val database = FirebaseDatabase.getInstance()
        var user = Firebase.auth.currentUser?.uid
        val itemsRef = database.getReference("user").child("$user")

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val itemList = mutableListOf<Travel>()

                for (itemSnapshot in dataSnapshot.children) {
                    val id = itemSnapshot.key.toString()
                    val title = itemSnapshot.child("title").value.toString()
                    val location = itemSnapshot.child("location").value.toString()
                    val note = itemSnapshot.child("note").value.toString()

                    val item = Travel(id, title, location, note)
                    itemList.add(item)
                }

                val adapter = RecyclerViewAdapter(itemList)
                val recyclerView = binding.rcycleViewTravel
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = adapter

                adapter.setOnItemLongClickListener(object :
                    RecyclerViewAdapter.OnItemLongClickListener {
                    override fun onItemLongClick(itemID: String) {
                        val builder = AlertDialog.Builder(requireContext())
                        builder.setTitle("Are you sure you want to delete the travel?")
                        builder.setPositiveButton("YES") { dialog, _ ->
                            itemsRef.child("$itemID").removeValue()
                            dialog.dismiss()
                        }
                        builder.setNegativeButton("NO") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                })

            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return root
    }
}