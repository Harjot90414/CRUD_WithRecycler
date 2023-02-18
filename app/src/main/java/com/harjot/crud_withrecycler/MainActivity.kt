package com.harjot.crud_withrecycler

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.harjot.crud_withrecycler.databinding.ActivityMainBinding
import com.harjot.crud_withrecycler.databinding.BtnNegativeDialogBinding
import com.harjot.crud_withrecycler.databinding.FabBtnDialogBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity(),RecyclerInterface {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:RecyclerAdapterClass
    var arrayList= ArrayList<UserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        adapter=RecyclerAdapterClass(arrayList,this)
        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter=adapter

        binding.btnFab.setOnClickListener {
            showDialog()
        }

    }
    override fun click(position: Int) {
        showDialog(position)
    }
    fun showDialog(position: Int = -1){
        var dialog=Dialog(this)
        var dialogBinding=BtnNegativeDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        if(position>-1){
            dialogBinding.btnDelete1.visibility = View.GONE
            dialogBinding.btnUpdate.setText("Add")
        }else{
            dialogBinding.btnUpdate.setText("Update")

        }
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        dialogBinding.btnUpdate.setOnClickListener {
            if (dialogBinding.etItemName.text.toString().trim().isNullOrEmpty()) {
                dialogBinding.etItemName.error = "Enter Name"
            } else {
                if(position>-1)
                    arrayList[position] = UserModel(dialogBinding.etItemName.text.toString())
                else{
                    arrayList.add( UserModel(dialogBinding.etItemName.text.toString()))
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
        dialogBinding.btnDelete1.setOnClickListener {
            arrayList.removeAt(position)
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
    }
}
