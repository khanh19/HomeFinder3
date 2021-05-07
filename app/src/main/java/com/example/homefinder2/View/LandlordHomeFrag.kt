package com.example.homefinder2.View

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homefinder2.Model.Property
import com.example.homefinder2.R
import com.example.homefinder2.ViewModel.RecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.fragment_landlord_home.*


/**
 * A simple [Fragment] subclass.
 */
class LandlordHomeFrag : Fragment() {
    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var auth:FirebaseAuth
    private val fireBase = FirebaseDatabase.getInstance()
    private var list = mutableListOf<Property>()
    private var adapter: RecyclerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landlord_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerAdapter(context!!, list)
        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView!!.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
        getListProp()
        add_btn.setOnClickListener {
            startActivityForResult(Intent(context, AddPropertyActivity::class.java), 1)
        }
    }

    private fun getListProp() {
        val ref = fireBase.reference.child("Property")
        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException())
                Toast.makeText(context, "Failed to load comments.",
                    Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {


                if(snapshot!!.exists()){
                    list.clear()
                    for (postSnapShot in snapshot.children){
                        val proper = postSnapShot.getValue<Property>()
                        list?.add(proper!!)
                    }
                    adapter!!.notifyDataSetChanged()

                }

            }
        })



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        auth = FirebaseAuth.getInstance()
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK)
            {

              getListProp()
            }

        }
    }

}
