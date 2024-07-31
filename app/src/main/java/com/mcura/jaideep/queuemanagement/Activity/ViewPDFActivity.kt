package com.mcura.jaideep.queuemanagement.Activity

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.listener.*
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import com.mcura.jaideep.queuemanagement.R
import com.mcura.jaideep.queuemanagement.Utils.Constant
import com.squareup.picasso.Picasso
import java.io.File

class ViewPDFActivity : AppCompatActivity() {
    private lateinit var patContact: String
    private lateinit var patName: String
    private lateinit var pdf: String
    private lateinit var pdf_viewer: PDFView
    private lateinit var progress_bar: ProgressBar
    private lateinit var pdfInternalFile: File
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_p_d_f)
        val intent = this.intent
        val bundle = intent.extras
        var mSharedPreference = getSharedPreferences(
            getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        pdf = bundle!!.getString("pdf").toString()
        val path = BuildConfig.BASE_URL + pdf
        mToolbar = findViewById(R.id.toolbar) as Toolbar
        progress_bar = findViewById(R.id.progress_bar)
        pdf_viewer = findViewById(R.id.pdf_viewer)
        if (mToolbar != null) {
            setSupportActionBar(mToolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
            supportActionBar!!.subtitle = ""
        }
        mToolbar.setNavigationOnClickListener(View.OnClickListener {
            // back button pressed
            onBackPressed()
            overridePendingTransition(R.anim.stay, R.anim.layout_slide_out_up)
        })
        var ivSubtanentLogo = findViewById<ImageView>(R.id.ivSubtanentLogo)
        val display = windowManager.defaultDisplay
        val width = display.width * 60 / 100 // ((display.getWidth()*20)/100)

        val parms = Toolbar.LayoutParams(width, Toolbar.LayoutParams.MATCH_PARENT)
        parms.gravity = Gravity.CENTER
        ivSubtanentLogo.setLayoutParams(parms)
        val subtanentImagePath: String =
            mSharedPreference.getString(Constant.SUB_TANENT_IMAGE_PATH, "")!!
        if (!TextUtils.isEmpty(subtanentImagePath)) {
            Picasso.with(this@ViewPDFActivity).load(subtanentImagePath).into(ivSubtanentLogo)
        } else {
            Picasso.with(this@ViewPDFActivity).load(R.drawable.logo).into(ivSubtanentLogo)
        }
        progress_bar.visibility = View.VISIBLE

        FileLoader.with(this)
            .load(path)
            .fromDirectory("PDFFiles", FileLoader.DIR_INTERNAL)
            .asFile(object : FileRequestListener<File> {
                override fun onLoad(
                    fileLoadRequest: FileLoadRequest,
                    fileResponse: FileResponse<File>
                ) {
                    progress_bar.visibility = View.GONE
                    pdfInternalFile = fileResponse.getBody()
                    pdf_viewer.fromFile(pdfInternalFile)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onDraw(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onDrawAll(object : OnDrawListener {
                            override fun onLayerDrawn(
                                canvas: Canvas,
                                pageWidth: Float,
                                pageHeight: Float,
                                displayedPage: Int
                            ) {
                            }
                        })
                        .onPageError(object : OnPageErrorListener {
                            override fun onPageError(page: Int, t: Throwable) {
                                Toast.makeText(this@ViewPDFActivity, "Error", Toast.LENGTH_LONG)
                                    .show()
                            }
                        })
                        .onPageChange(object : OnPageChangeListener {
                            override fun onPageChanged(page: Int, pageCount: Int) {
                            }
                        })
                        .onTap(object : OnTapListener {
                            override fun onTap(e: MotionEvent): Boolean {
                                return true
                            }
                        })
                        .onRender(object : OnRenderListener {
                            override fun onInitiallyRendered(
                                nbPages: Int,
                                pageWidth: Float,
                                pageHeight: Float
                            ) {
                                pdf_viewer.fitToWidth()
                            }
                        })
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load()
                }

                override fun onError(fileLoadRequest: FileLoadRequest, throwable: Throwable) {
                    Toast.makeText(this@ViewPDFActivity, "Error", Toast.LENGTH_LONG).show()
                    progress_bar.setVisibility(View.GONE)
                }
            })
    }

    override fun onDestroy() {
        Log.d("onDestroy", "onDestroy");
        if (pdfInternalFile != null) {
            var status = pdfInternalFile.delete()
            Log.d("onDestroy", status.toString());
        }
        super.onDestroy()
    }
}
