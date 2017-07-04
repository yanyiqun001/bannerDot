# bannerDot
贝塞尔圆点指示器

![image](https://github.com/yanyiqun001/bannerDot/blob/master/screenshots/ezgif.com-resize%20(2).gif?raw=true)





 可以拷贝 
[源码](https://github.com/yanyiqun001/bannerDot/blob/master/app/src/main/java/com/bannerdot/BezierBannerDot.java)和
[资源文件](https://github.com/yanyiqun001/bannerDot/blob/master/app/src/main/res/values/attr.xml)直接使用

##### xml中(替换成自己的路径)：
```
       <com.bannerdot.BezierBannerDot
        android:id="@+id/bd"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:paddingBottom="20dp"
        app:selectedRaduis="13dp"
        app:unSelectedRaduis="10dp"
        app:selectedColor="@android:color/white"
        app:unSelectedColor="@android:color/darker_gray"
        app:spacing="20dp"
        android:layout_alignParentBottom="true"
        />
```      

 selectedRaduis：选中圆半径
 
 unSelectedRaduis：背景圆半径
 
 selectedColor：选中圆颜色
 
 selectedColor：背景圆颜色 
 
 spacing：间距
 
##### 代码中绑定viewpager
```
bezierBannerDot.attachToViewpager(viewPager)
```
