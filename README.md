## Gradle
[![](https://jitpack.io/v/zj565061763/viewpager.svg)](https://jitpack.io/#zj565061763/viewpager)

## 本库主要解决的问题
* 在xml中给ViewPager设置高度为wrap_content的时候无法包裹内容<br>
`解决方案：使用FViewPager`

* 当ViewPager嵌套ViewPager的时候，有些情况下不希望内部的ViewPager滚动到边界的时候继续拖动导致外部的ViewPager发生滚动<br>
`解决方案：使用FViewPager.addPullCondition(pullCondition)方法来给外部的ViewPager设置一个拖动条件限制，当拖动的触摸点在内部ViewPager可见范围之内的时候返回false不允许拖动`
  
## FGridViewPager
需求：实现类似微信表情这种每一页都是一个网格布局<br>
![](http://thumbsnap.com/s/UE9uaWoJ.png?0810)<br>

正常情况下的实现步骤：
1. 准备好总的表情实体集合比如List\<ImageModel\> listModel
2. 新建一个ViewPager需要用到的适配器ImagePagerAdapter，继承自android.support.v4.view.PagerAdapter
3. 新建一个每一页中GridView需要用到的适配器ImageItemAdapter，继承自android.widget.BaseAdapter
4. 给ViewPager设置ImagePagerAdapter对象

在上述步骤总比较繁琐的地方是第2步中的适配器内部绑定数据的时候需要根据每一页一共要展示几个数据，当前是第几页来计算出当前页需要从listModel中取实体的范围。FGridViewPager就是为了解决上述繁琐的过程而实现的类<br>

用FGridViewPager的实现步骤：
1. 准备好总的表情实体集合比如List\<ImageModel\> listModel
2. 新建一个每一页中需要用到的适配器ImageItemAdapter，继承自android.widget.BaseAdapter
3. 给FGridViewPager设置ImageItemAdapter对象

FGridViewPager还支持设置分割线，具体见源码

```java
mViewPager.setGridItemCountPerPage(9); //设置每一页要显示多少个数据
mViewPager.setGridColumnCountPerPage(3); //设置每一页有几列
mViewPager.setGridAdapter(mItemAdapter); //设置适配器，适配器继承自android.widget.BaseAdapter
```
