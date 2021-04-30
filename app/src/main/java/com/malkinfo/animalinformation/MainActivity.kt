package com.malkinfo.animalinformation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.malkinfo.animalinformation.adapter.AnimalsAdapter
import com.malkinfo.animalinformation.model.AnimalData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mDataBase:DatabaseReference
    private lateinit var animaList:ArrayList<AnimalData>
    private lateinit var mAdapter:AnimalsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**initialized*/
        animaList = ArrayList()
        mAdapter = AnimalsAdapter(this,animaList)
        recyclerAnimals.layoutManager = LinearLayoutManager(this)
        recyclerAnimals.setHasFixedSize(true)
       // recyclerAnimals.adapter = mAdapter
        /**getData firebase*/
        getAnimalsData()

    }
    /**ok now create new activity*/


    private fun getAnimalsData() {

        mDataBase = FirebaseDatabase.getInstance().getReference("Animals")
        mDataBase.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               if (snapshot.exists()){
                   for (animalSnapshot in snapshot.children){
                       val animal = animalSnapshot.getValue(AnimalData::class.java)
                       animaList.add(animal!!)
                   }
                   recyclerAnimals.adapter = mAdapter
               }
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(this@MainActivity,
                   error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }




}