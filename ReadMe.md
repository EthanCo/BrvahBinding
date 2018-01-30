基于BaseRecyclerViewAdapterHelper实现的Recyclerview DataBinding Library，更改数据自动反馈到Recyclerview上。  
详见`DATABINDINGUSERADAPTER`

![BrvahBinding](http://oqk78xit2.bkt.clouddn.com/brvahbinding.gif)  

## Usage
### Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:  

	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}  

### Step 2. Add the dependency  
	
	dependencies {
		compile 'com.github.EthanCo:BrvahBinding:1.0.0'
	}
