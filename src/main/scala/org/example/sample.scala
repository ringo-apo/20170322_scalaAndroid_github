package org.example


import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.view.View
import _root_.android.widget.Button

class MainActivity extends Activity with TypedFindView {
    lazy val editText1 = findView(TR.editText1)
    lazy val textView1 = findView(TR.textView1)
    lazy val editText2 = findView(TR.editText2)
    lazy val textView2 = findView(TR.textView2)

    /** Called when the activity is first created. */
    override def onCreate(savedInstanceState: Bundle): Unit = {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val button1 = findViewById(R.id.button1).asInstanceOf[Button]

        button1.setOnClickListener(new View.OnClickListener() {
            def onClick(v : View) {

                val keisan = (editText1.getText.toString).toInt + (editText2.getText.toString).toInt
                textView2.setText(keisan.toString)

            }
        })

    }
}