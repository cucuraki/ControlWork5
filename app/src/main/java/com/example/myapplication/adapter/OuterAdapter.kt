package com.example.myapplication.adapter

import android.app.DatePickerDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.parser.Input
import com.example.myapplication.databinding.TestForRecyclerBinding
import java.util.*

class OuterAdapter : RecyclerView.Adapter<OuterAdapter.OuterViewHolder>() {
    private lateinit var list: List<List<Input>>
    private lateinit var changeListener: (String, Int) -> Unit
    private var count = 0

    fun setTextListener(listener: (String, Int) -> Unit) {
        changeListener = listener
    }

    fun setData(list: List<List<Input>>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OuterViewHolder(
        TestForRecyclerBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: OuterViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = list.count()


    inner class OuterViewHolder(private val binding: TestForRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val displayMetrics = binding.root.context.resources.displayMetrics
            for (i in list[adapterPosition]) {
                val ind = count
                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    displayMetrics.widthPixels - 150,
                    (60*displayMetrics.density).toInt()
                )
                params.setMargins(10, 20, 10, 20)
                with(binding.root) {
                    if (i.fieldType != "chooser") {
                        val image = ImageView(context)
                        val editText = EditText(context)
                        editText.inputType = when (i.keyboard) {
                            "text" -> InputType.TYPE_CLASS_TEXT
                            "number" -> InputType.TYPE_CLASS_NUMBER
                            else -> InputType.TYPE_CLASS_TEXT
                        }
                        editText.hint = i.hint
                        editText.layoutParams = params

                        editText.doOnTextChanged { text, _, _, _ ->
                            changeListener(text.toString(), ind)
                        }
                        addView(editText)
                        val imageParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                            (30*displayMetrics.density).toInt(),
                            (30*displayMetrics.density).toInt()
                        )
                        imageParams.setMargins(displayMetrics.widthPixels - 200, -110,0,20)
                        image.layoutParams = imageParams
                        image.setImageResource(R.drawable.ic_launcher_background)
                        addView(image)
                    } else {
                        if (i.hint == "Birthday") {
                            val btn = Button(context)
                            btn.layoutParams = params
                            btn.text = i.hint
                            addView(btn)
                            btn.setOnClickListener {
                                val myCalendar = Calendar.getInstance()
                                val myYear = myCalendar.get(Calendar.YEAR)
                                val myMonth = myCalendar.get(Calendar.MONTH)
                                val day = myCalendar.get(Calendar.DAY_OF_MONTH)
                                val dpd = DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        val text = "$dayOfMonth.${month + 1}.$year"
                                        btn.text = text
                                        changeListener(text, ind)
                                    },
                                    myYear,
                                    myMonth,
                                    day
                                )
                                dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
                                dpd.show()
                            }
                        } else if (i.hint == "Gender") {
                            val spinner = Spinner(context)
                            spinner.layoutParams = params
                            val list = arrayOf("Gender", "Male", "Female")
                            spinner.adapter = ArrayAdapter(
                                context,
                                android.R.layout.simple_spinner_dropdown_item,
                                list
                            )
                            spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
                                override fun onItemSelected(
                                    p0: AdapterView<*>?,
                                    p1: View?,
                                    p2: Int,
                                    p3: Long
                                ) {
                                    changeListener(list[p2],ind)
                                }
                                override fun onNothingSelected(p0: AdapterView<*>?){}
                            }
                            addView(spinner)
                        }
                    }
                }
                count++
            }
        }
    }
}
