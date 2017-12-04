# bannerDot
贝塞尔圆点指示器

![image](https://github.com/yanyiqun001/bannerDot/blob/master/screenshots/ezgif.com-resize%20(2).gif?raw=true)

### Usage

Add the JitPack repository to your build file

```
allprojects {
          repositories {
              ...
              maven { url "https://jitpack.io" }
          }
      }
```
 Add the dependency
```
  
dependencies{
    compile 'com.github.yanyiqun001:bannerDot:1.0'
}

```
 

##### xml：
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

 selectedRaduis：起始圆半径
 
 unSelectedRaduis：背景圆半径
 
 selectedColor：起始圆颜色
 
 unSelectedColor：背景圆颜色 
 
 spacing：间距
 
##### Attach to viewpager
```
bezierBannerDot.attachToViewpager(viewPager)
```
