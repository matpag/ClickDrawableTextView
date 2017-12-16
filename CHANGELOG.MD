### v3.0.0 - 16/12/2017
 - Fix a problem with ```disableFocusOnText()``` method, with some keyboard application like SwiftKey the text underline didn't disappear after choice selection
 - Add ```cs{Position}DrawableTint``` XML property to set a tint specific to a ```CsDrawable```
 - Add ```cs{Position}DrawableTintMode``` XML property to set a tintMode specific to a ```CsDrawable```
 - Add new constructors to ```CsDrawable.Builder``` to give the ability to create CsDrawable with locally mutable drawables
 - Remove ```CsDrawable.Builder(@NonNull Context context, @DrawableRes int drawableRes)``` method and replaced it with
   ```CsDrawable.Builder(@NonNull Context context, @DrawableRes int drawableRes, boolean mutable)```. If you were using
   the removed method you can simply add ```false``` as the latest parameter and everything will works again as usual.
 - Remove ```ClickableDrawable``` interface from the public api
 - Update AGP to 3.0.1
 - Update Build Tools to 27.0.2
 - Update Support Library to 27.0.2
 - Update ```compileSdk``` and ```targetSdk``` to 27
##
### v2.0.2 - 11/05/2017
 - Removed unused string
 - Small optimization to ```CsDrawableSettings.init(...)``` method
##
### v2.0.0 - 11/05/2017
 - Update Gradle Wrapper to 4.2.1
 - Update AGP to 3.0.0
 - Update Build Tools to 27.0.0
 - Update Support Library to 27.0.0
##
### v1.0.0 - 05/30/2017
 - minSdk lowered to API 16 (by default API 16 will have RTL support disabled)
##
### v0.6.0 - 05/27/2017
 - First release. Yuppy!!!!
