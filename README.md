# ProgressView

>>圆形进度控件

	    <com.ufo.costomview.view.ProgressView
        android:id="@+id/scanView"
        android:layout_centerInParent="true"
        android:layout_width="100dp"
        scanview:text_color="@android:color/white"
        android:layout_height="100dp" />

-

	<resources>
	    <declare-styleable name="ProgressView">
	        <attr name="circle_bgColor" format="reference" />
	        <attr name="sweep_circelColor" format="reference" />
	        <attr name="text_color" format="reference" />
	    </declare-styleable>
	</resources>

![](http://i.imgur.com/02QYr2z.gif)