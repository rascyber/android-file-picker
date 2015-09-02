# Introduction #

Here is a quick overview over how to use this file picker in your app.

# Details #

## Add the file picker as a library to your project in Eclipse ##

Project properties -> Android -> scroll down to libraries -> Click "Add"

## Add the following activity to your AndroidManifest.xml ##

```
<activity 
    android:name="name.vbraun.filepicker.FilePickerActivity"
    android:theme="@android:style/Theme.Holo.Light">
</activity>
```

## Call the file picker from your activity ##

```
    protected static final int MY_REQUEST_CODE = 1;

    public boolean pickFile() {
        Intent intent = new Intent(getApplicationContext(), name.vbraun.filepicker.FilePickerActivity.class);
        intent.setAction("org.openintents.action.PICK_FILE");
        intent.putExtra("org.openintents.extra.TITLE", "Pick a file");
        startActivityForResult(intent, MY_REQUEST_CODE);
    }
```

or use `org.openintents.action.PICK_DIRECTORY` to pick a directory.

## Handle the result in your activity ##

```

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case MY_REQUEST_CODE:
             String fileName = filenameFromActivityResult(resultCode, data);
             // user picked fileName
             break;
        }
    }
```