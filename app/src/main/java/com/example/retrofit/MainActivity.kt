package com.example.retrofit

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofit.adapters.RvAdapter
import com.example.retrofit.databinding.ActivityMainBinding
import com.example.retrofit.databinding.ItemDialogBinding
import com.example.retrofit.models.MyPostRequest
import com.example.retrofit.models.MyTodo
import com.example.retrofit.network.MyApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RvAdapter.RvAction {
    lateinit var rvAdapter: RvAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAad.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)

            itemDialogBinding.apply {
                btnSave.setOnClickListener {
                    val myPostRequest = MyPostRequest(
                        false,
                        edtAbout.text.toString(),
                        edtTitle.text.toString()
                    )
                    MyApiClient.getApiService().addTodo(myPostRequest)
                        .enqueue(object : Callback<MyTodo>{
                            override fun onResponse(
                                call: Call<MyTodo>,
                                response: Response<MyTodo>,
                            ) {
                                if (response.isSuccessful){
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Salqandi",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                    onResume()
                                    dialog.cancel()
                                }
                            }

                            override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                                Toast.makeText(
                                    this@MainActivity,
                                    "Xatolik yuz berdi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            }

            dialog.setView(itemDialogBinding.root)
            dialog.show()
        }

    }

    override fun onResume() {
        super.onResume()
        binding.progress.visibility = View.VISIBLE
        MyApiClient.getApiService()
            .getAllTodo().enqueue(object : Callback<List<MyTodo>>{
                override fun onFailure(call: Call<List<MyTodo>>, t: Throwable){
                    Toast.makeText(this@MainActivity,"Error", Toast.LENGTH_SHORT).show()
                    binding.progress.visibility = View.INVISIBLE
                }

                override fun onResponse(
                    call: Call<List<MyTodo>>,
                    response: Response<List<MyTodo>>,
                ) {
                    if (response.isSuccessful){
                        val list = response.body()
                        if (list!=null){
                            rvAdapter = RvAdapter(list, this@MainActivity)
                            binding.rv.adapter = rvAdapter
                            binding.progress.visibility = View.INVISIBLE
                        }
                    }
                }
            })
    }

    override fun deleteTodo(myTodo: MyTodo) {
        MyApiClient.getApiService().deleteTodo(myTodo.id)
            .enqueue(object : Callback<Any>{
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    if (response.isSuccessful){
                        Toast.makeText(this@MainActivity, "O'chirildi", Toast.LENGTH_SHORT).show()
                        onResume()
                    }
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Xatolik", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun editTodo(myTodo: MyTodo) {
        val dialog = AlertDialog.Builder(this).create()
        val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)

        itemDialogBinding.apply {
            edtTitle.setText(myTodo.sarlavha)
            edtAbout.setText(myTodo.izoh)

            btnSave.setOnClickListener {
                val myPostRequest = MyPostRequest(

                    true,
                    edtAbout.text.toString(),
                    edtTitle.text.toString()

                )

                MyApiClient.getApiService().updateTodo(myTodo.id, myPostRequest)
                    .enqueue(object : Callback<MyTodo>{
                        override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                            if (response.isSuccessful){
                                Toast.makeText(this@MainActivity, "Tahrirlandi", Toast.LENGTH_SHORT)
                                    .show()
                                onResume()
                                dialog.cancel()
                            }
                        }

                        override fun onFailure(call: Call<MyTodo>, t: Throwable) {
                            Toast.makeText(this@MainActivity, "Xatolik", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }

        dialog.setView(itemDialogBinding.root)
        dialog.show()
    }
}