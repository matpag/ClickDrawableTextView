# ClickDrawable - TextView/EditText/AutoComplete

This library add the ability to listen for drawable click events for TextView, EditText and AutoCompleteTextView normally added in XML
with ```android:drawableStart``` etc... or in code with ```setCompoundDrawables(...)``` and similar.
Also with this library you can flat a layout used to side any widget with an ```ImageView``` or ```ImageButton``` added to listen for click on the icon, to one unique custom view.

For example converting this XML:
```
<LinearLayout 
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">
    <TextView
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_gravity="center_vertical"
            android:layout_height="wrap_content"
            android:text="this textview looks nice"/>
    <ImageView
            android:layout_width="30dp"
            android:layout_height="30dp"
            app:srcCompat="@drawable/my_icon
            android:text="this textview looks nice"/>
</LinearLayout> 
```
Into this:
```
<com.matpag.clickdrawabletextview.ClickDrawableTextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="this textview looks nice"
        app:csStartDrawable="@drawable/my_icon
        app:csStartDrawableHeight="30dp"
        app:csStartDrawableWidth="30dp"/>
```
Removing a ViewGroup, removing inefficient weights and adding a lot more functionalities.

## Demo
This GIF shows a ```ClickDrawableEditText``` with clickable drawables all around it (please never do this in a real app, it's really unpleasant :D) and a ```ClickDrawableAutoCompleteTextView``` with support for blocking inputs after user choice, until cancel icon is pressed.

![Library showcase](https://media.giphy.com/media/xUA7aXqmppLR7a0JfG/giphy.gif)

## Setup

This library is minSdk 17 because of [this](https://developer.android.com/reference/android/content/res/Configuration.html#SCREENLAYOUT_LAYOUTDIR_RTL) flag we need to know if the app is running in RTL mode or not.

### 1. Add dependency in build.gradle
```compile 'com.matpag:clickdrawabletextview:0.6.0'```

### 2. Use a support theme
Use one of the theme from ```Theme.AppCompat.*``` for your application theme.

### 3. Initialize library in custom ```Application```
Call ```CsDrawableSettings.init(Context context, String packageName)``` in the ```onCreate``` of your custom ```Application``` class. 
In this way:
```java
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Init the library
        CsDrawableSettings.init(this, BuildConfig.APPLICATION_ID);
    }
}
```
then you need to change your ```AndroidManifest.xml``` to load your custom
application class instead of the default one adding the
```android:name".AppApplication"``` (you should update the path accordingly based
on where you create the ```AppApplication``` class) to the manifest if you already didn't.

Something similar to this:
```
<application
        android:name=".AppApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:supportsRtl="true"
        ....>

        <!-- Other declarations -->

</application>
```

### 4. RTL support
If you want to support RTL, you simply need to add ```android:supportsRtl="true"``` to the *application* tag in your ```AndroidManifest.xml``` and the library will handle everything for you.

## Docs

The library provides 3 custom views with extended functionalities for drawables.

#### ```ClickDrawableTextView```
#### ```ClickDrawableEditText```
#### ```ClickDrawableAutoCompleteTextView```

Each of them it's extending the ```AppCompat``` counterpart class for max compatibility with every supported SDK version

The custom drawables are ```CsDrawable```s objects.

You can add a ```CsDrawable``` via XML or via Code, below you can find both the implementations.

#### XML
After declaring
```
xmlns:app="http://schemas.android.com/apk/res-auto"
```
in the top parent layout, you can specify the custom properties shown below in any of the
supported views.

To add the drawable to the view in position you need, you can use:
```
app:csStartDrawable
app:csEndDrawable
app:csTopDrawable
app:csBottomDrawable
```
providing a reference to a drawable of any type (png, xml drawable, vector drawable)

If you don't specify any size for the drawable, automatically the drawable will be sized to the width and height of the drawable itself (called **intrinsic** in the android platform).

If you dont want to resize your original drawable resource (and you really shouldn't if not necessary), i suggest you to specify the width and the height in **dp** you need using:
```
app:csStartDrawableHeight
app:csStartDrawableWidth
```
for the **Start** drawable
```
app:csEndDrawableHeight
app:csEndDrawableWidth
```
for the **End** drawable
```
app:csTopDrawableHeight
app:csTopDrawableWidth
```
for the **Top** drawable
```
app:csBottomDrawableHeight
app:csBottomDrawableWidth
```
for the **Bottom** drawable.

If you need to handle the initial visibility of the drawable you can use
```
app:csStartDrawableVisible
app:csEndDrawableVisible
app:csTopDrawableVisible
app:csBottomDrawableVisible
```
with ```true```(default) or ```false``` value

#### Code

Use this XML as example
```
<com.matpag.clickdrawabletextview.ClickDrawableEditText
        android:id="@+id/click_drawable_edit_text"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"/>
```
Get the reference in code as usual

```ClickDrawableEditText mCdEditText = (ClickDrawableEditText)findViewById(R.id.click_drawable_edit_text);```

Create a ```CsDrawable``` object using the builder class

```
CsDrawable csDrawable = new CsDrawable.Builder(this, R.drawable.ic_close_red_24dp)
                .setDrawableDpSize(30, 30)
                .setVisibility(false)
                .build();
```

Add the CsDrawable to the view with one of the following methods
```mCdEditText.addStartCsDrawable(csDrawable);```

## Author

* **Mattia Pagini**

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

