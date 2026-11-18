package com.example.expensenote_34

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    //สร้างตัวแปรสำหรับหน้า UI Element
    private lateinit var etAmount: EditText
    private lateinit var etNote: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnClear: Button
    private lateinit var tvLastItem: TextView
    private lateinit var tvTotal: TextView

    //สร้างตัวแปรสำหรับเก็บค่ายอดรวม
    private var total: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //เชื่อมโยงตัวแปรกับหน้า XML (แบบระบุ ID)
        etAmount = findViewById<EditText>(R.id.etAmount)
        etNote = findViewById<EditText>(R.id.etNote)
        btnClear = findViewById<Button>(R.id.btnClear)
        btnAdd = findViewById<Button>(R.id.btnAdd)
        tvLastItem = findViewById<TextView>(R.id.tvLastItem)
        tvTotal = findViewById<TextView>(R.id.tvTotal)

        btnAdd.setOnClickListener {
            //ดึงข้อความจากช่องกรอกจำนวนเงิน และห้องหมายเหตุ พร้อมตัดช่องว่างออก
            val amountText = etAmount.text.toString().trim()
            val noteText = etNote.text.toString().trim()

            //ตรวจสอบเงื่อนไข ผู้ใช้งานได้ทำการกรอกจำนวนเงินมาแล้วหรือยัง
            if (amountText.isEmpty()){
                Toast.makeText(this,"กรุณากรอกจำนวนเงิน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //ตรวจสอบเงื่อนไข ผู้ใช้งานทำการกรอกจำนวนเงินเป็นตัวเลขหรือไม่
            val amount = amountText.toDoubleOrNull()
            if (amount == null){
                Toast.makeText(this,"กรุณากรอกจำนวนเงินเป็นตัวเลข", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //ทำการบวกจำนวนเงินเข้าไปในยอดสะสม
            total += amount

            //ตรวจสอบเงื่อนไข ผู้ใช้งานทำการกรอกคำอธิบายหรือไม่ หากไม่ให้กำหนดเป็น "ไม่ระบุ"
            val showNote = if (noteText.isNotEmpty()) noteText else "ไม่ระบุ"

            //แสดงข้อมูล
            tvLastItem.text = "รายการล่าสุด : $showNote - %.2f บาท".format(amount)
            tvTotal.text = "%.2f".format(total)
            //ลบข้อความหลังบันทึกข้อมูล
            etNote.text.clear()
            etAmount.text.clear()
        }
        btnClear.setOnClickListener {
            total = 0.0

            etNote.text.clear()
            etAmount.text.clear()

            tvTotal.text = "0.00"
            tvLastItem.text = "รายการล่าสุด : -"
        }
    }
}