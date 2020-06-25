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
Create a object class and name it 'MyListItemTypes' (Name it as you like), this class will hold the types the viewholders that will be used in the app.
Note that you can create many files of this
```
object MyListItemTypes {
    // Keep this at first, so in case there is a listItem with no type then this will be the default
    var NONE = ListItemType(BlankViewHolder::class.java, 0, "NONE")
    
    // Your types
    val ITEM_TYPE_1 = ListItemType(Type1ViewHolder::class.java, R.layout.row_type_1, "ITEM_TYPE_1")
    val ITEM_TYPE_2 = ListItemType(Type2ViewHolder::class.java, R.layout.row_type_2, "ITEM_TYPE_2")
}
```

### Models
Let your model extend Listitem and pass the ItemType in the constructor
```
class ModelType1(val text: String? = "") : ListItem(ListItemTypes.ITEM_TYPE_1)
class ModelType2(val image: String? = "") : ListItem(ListItemTypes.ITEM_TYPE_2)
...
```

### ViewHolder
Create your viewholder and extend it with BaseViewHolder<YourModelHere>
```
class Type1ViewHolder(private val binding: ViewDataBinding, callback: ListItemCallback?) : BaseViewHolder<ModelType1>(binding, callback) {

    init {
        attachClickListener(itemView)
    }

    override fun draw(listItem: ModelType1) {
        super.draw(listItem)
        binding.setVariable(BR.model, listItem)
        binding.executePendingBindings()
    }
}
```

### Initalize adapter
```
private val adapter: GeneralListAdapter by lazy {
    GeneralListAdapter(context = context, listItemCallback = this)
}
```

### Recyclerview
Bind your recyclerview with the adapter
```
recyclerview?.adapter = adapter
```

### Display
Now you just need to add any model to the adpater and it will be added to the adapter with its viewholder
```
val myList =  listOf(
    ModelType1(text = "Hello :)"),
    ModelType2(image = sampleBackgrounds.random())
)
adapter.addAll(myList)
```

### Callback listener
```
override fun onItemClicked(view: View, listItem: ListItem?, position: Int, actionId: Int?) {
    when (listItem) {
        is ModelType1 -> showDialog(listItem.text ?: "")
        is ModelType2 -> showDialog(listItem.text ?: "")
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
    
