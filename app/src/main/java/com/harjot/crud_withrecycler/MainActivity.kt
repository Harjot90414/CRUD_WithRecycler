package com.harjot.crud_withrecycler

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.harjot.crud_withrecycler.databinding.ActivityMainBinding
import com.harjot.crud_withrecycler.databinding.BtnNegativeDialogBinding

class MainActivity : AppCompatActivity() ,RecyclerInterface {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter:RecyclerAdapterClass
    var arrayList= ArrayList<UserModel>()
    var showList= ArrayList<UserModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        showList.addAll(arrayList)

        adapter = RecyclerAdapterClass(showList, this)
        binding.rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rv.adapter = adapter

        binding.btnFab.setOnClickListener {
            showDialogView()
        }

    }
    override fun click(position: Int) {
        showDialogView(position)
    }
     fun showDialogView(position: Int = -1){
         var dialog= Dialog(this)
         var dialogBinding= BtnNegativeDialogBinding.inflate(layoutInflater)
         dialog.setContentView(dialogBinding.root)
         if(position==-1){
             dialogBinding.tvText.setText("Add Item")
             dialogBinding.btnDelete1.visibility = View.GONE
             dialogBinding.btnUpdate.setText("Add")
         }else{
             dialogBinding.tvText.setText("Update Item")
             dialogBinding.btnUpdate.setText("Update")
             dialogBinding.etItemName.setText(arrayList[position].name)
             dialogBinding.etAddress.setText(arrayList[position].address)
             dialogBinding.etPhonNo.setText(arrayList[position].phoneNo.toString())
         }
         dialog.window?.setLayout(
             WindowManager.LayoutParams.MATCH_PARENT,
             WindowManager.LayoutParams.WRAP_CONTENT
         )



         dialogBinding.btnUpdate.setOnClickListener {
             if (dialogBinding.etItemName.text.toString().trim().isNullOrEmpty()) {
                 dialogBinding.etItemName.error = "Enter Name"
             }
             else if(dialogBinding.etAddress.text.toString().trim().isNullOrEmpty()){
                 dialogBinding.etAddress.error="Enter Address"
             }
             else if(dialogBinding.etPhonNo.length()<10){
                 dialogBinding.etPhonNo.error="Invalid Phone No."
             }
             else if(dialogBinding.etPhonNo.text.toString().trim().isNullOrEmpty()){
                 dialogBinding.etPhonNo.error="Enter PhoneNo"
             }
             else {
                 if(position>-1) {
                     arrayList[position] = UserModel(
                         dialogBinding.etItemName.text.toString(),
                         dialogBinding.etAddress.text.toString(),
                         dialogBinding.etPhonNo.text.toString()
                     )
                 }
                 else{
                     showList.clear()
                     arrayList.add(UserModel(
                         dialogBinding.etItemName.text.toString(),
                         dialogBinding.etAddress.text.toString(),
                         dialogBinding.etPhonNo.text.toString()
                     ))
                     showList.addAll(arrayList)
                 }
                 adapter.notifyDataSetChanged()
                 dialog.dismiss()
             }
         }

         dialogBinding.btnDelete1.setOnClickListener {
             showList.removeAt(position)
             arrayList.removeAt(position)
             adapter.notifyDataSetChanged()
             dialog.dismiss()
         }
         dialog.show()
     }
}
