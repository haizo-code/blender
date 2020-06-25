<p align="center"><img src="/device-2020-06-25-105017.png" width="250" align="right" vspace="24"></p>

Recyclerview General Adapter
=================
[![](https://jitpack.io/v/Haizo94/recyclerview-general-adapter.svg)](https://jitpack.io/#Haizo94/recyclerview-general-adapter)
[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)

Android library project that intends to simplify the usage of Adapters for recyclerView using **Data Binding**. You won't have to code any adapter again!

## Gradle

**Step 1.** Add the JitPack repository to your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the library dependency to your project build.gradle:
```
dependencies {
    implementation 'com.github.Haizo94:recyclerview-general-adapter:v1.0.0'
}
```

## Sample code:

### Types file
Create a object class and name it 'ListItemTypes' (Name it as you like), this class will hold the types of the *ViewHolders* that will be used in the app.
Note that you can create many files of this
```
object ListItemTypes {
    // Your types
    val ITEM_TYPE_1 = ListItemType(Type1ViewHolder::class.java, R.layout.row_type_1, "ITEM_TYPE_1")
    val ITEM_TYPE_2 = ListItemType(Type2ViewHolder::class.java, R.layout.row_type_2, "ITEM_TYPE_2")
}
```

### Models
Let your model implements ListItem and override the ListItemType
```
class ModelType1(val text: String?) : ListItem {
    override var listItemType: ListItemType? = ListItemTypes.ITEM_TYPE_1
}

class ModelType2(val imageUrl: String?) : ListItem {
    override var listItemType: ListItemType? = ListItemTypes.ITEM_TYPE_2
}
...
```
**NOTE: if the listItemType for a model is *NULL* then the viewHolder will not be loaded only without any *CRASH* :)**

### ViewHolder
Create your viewholder and extend it with BaseViewHolder<YourModelHere>
```
class Type1ViewHolder(private val viewDataBinding: ViewDataBinding, callback: ListItemCallback?) :
    BaseBindingViewHolder<ModelType1>(binding = viewDataBinding, callback = callback) {

    init {
        attachClickListener(itemView)
    }

    override fun draw(listItem: ModelType1) {
        super.draw(listItem)
        viewDataBinding.setVariable(BR.model, listItem)
        viewDataBinding.executePendingBindings()
    }
}
```

### Initializing the adapter
```
private val adapter: GeneralBindingListAdapter by lazy {
    GeneralBindingListAdapter(context = this, listItemCallback = this)
}
```

### Recyclerview
Bind your recyclerview with the adapter
```
recyclerview?.adapter = adapter
```

### Display
Now you just need to add any model to the adapter and it will be added to the adapter with its ViewHolder, and that's it :)
```
val myList =  listOf(
    ModelType1(text = "Hello :)"),
    ModelType2(image = sampleBackgrounds.random())
)
adapter.addAll(myList)
```

### Callback listener
```
override fun onItemClicked(view: View, listItem: ListItem, position: Int, actionId: Int) {
    when (listItem) {
        is ModelType1 -> toast(listItem.text)
        is ModelType2 -> toast(listItem.imageUrl)
    }
}
```

# License

    Copyright 2020 Farouq Afghani

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
