## ClickDrawable - TextView/EditText/AutoComplete

This library add the ability to listen for drawable click events for TextView, EditText and AutoCompleteTextView normally added in XML
with ```android:drawableStart``` etc... or in code with ```setCompoundDrawables(...)``` and similar.
With this library you can flat a layout using every Widget and an ```ImageView``` or ```ImageButton``` on one side to listen for click on the image to one unique view.

For example converting this XML:
```
<LinearLayout 
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">
    <TextView
            android:layout_width="0dp"
            android:layout_weight="1"
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
Removing inefficient weights and adding a lot more functionalities.

## Demo

![Library showcase](https://media.giphy.com/media/xUA7aXqmppLR7a0JfG/giphy.gif)

## Setup

### 1. Add dependency in build.gradle
```compile 'com.matpag:clickdrawabletextview:0.6.0'```

### 2. Use a support theme
Use one of the theme from ```Theme.AppCompat.*``` for your application theme.

### 3. Initialize library in custom ```Application```
Call ```CsDrawableSettings.init(Context context, String packageName)``` in the ```onCreate``` of your custom ```Application``` class. In this way:
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

I suggest you to init the library at the end of ```onCreate()``` method of your
 custom ```Application``` class,
If you dont have a custom ```Application``` class, you can create one like this:


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
on where you create the ```AppApplication``` class) to the manifest.

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


## Docs

The library provides 3 views with extended functionalities for drawables.

Those are:

```ClickDrawableTextView```

```ClickDrawableEditText```

```ClickDrawableAutoCompleteTextView```

Each of them it's extending the ```AppCompat``` counterpart for max compatibility with every SDK version.

The custom drawables are ```CsDrawable```s objects.

You can add a ```CsDrawable``` via XML or via Code,
below you can find both the implementations

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
providing a reference to a drawable of any type.

If you don't specify any size for the drawable, automatically the drawable will be sized
to the width and height of the drawable itself (called **intrinsic** in the android
platform).

If you dont want to resize your original drawable resource, i suggest you to
add the width and the height you need for the drawable/s you added using respectively:
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

Don't forget to enable the RTL support in your ```AndroidManifest.xml``` adding
```android:supportsRtl="true"``` to the *application* tag if you care about RTL.

#### Code

You need to create or reference one of the library ```TextView``` subclasses.

You can have this XML
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
```mCdEditText.addEndCsDrawable(csDrawable);

## Author

* **Mattia Pagini**
See also the list of [contributors](https://github.com/matpag/ClickDrawableTextView/contributors) who participated in this project.

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details

