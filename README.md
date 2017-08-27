## Gradle
`compile 'com.fanwe.android:viewpager:1.0.12'`

## 本库主要解决的问题
* 问题1：在xml中给ViewPager设置高度为wrap_content的时候无法包裹内容<br>
`解决方案：使用SDViewPager`

* 问题2：当我们需要监听Adapter的数据发生变化的时候，给Adapter注册了一个DataSetObserver对象，那么当ViewPager被设置新的Adapter对象的时候，需要先对旧的Adapter取消注册DataSetObserver对象，再对新的对象注册DataSetObserver对象，处理比较繁琐<br>
`解决方案：使用SDViewPagerInfoListener`
  
* 问题3：当我们需要给ViewPager添加滚动指示器来指示当前滚动到第几页，一共有几页。设置Adapter的时候或者Adapter的内容发生变化的时候总页数都可能发生变化，处理比较繁琐<br>
`解决方案：使用SDViewPagerInfoListener`

* 问题4：当ViewPager嵌套ViewPager的时候，有些情况下不希望内部的ViewPager滚动到边界的时候继续拖动导致外部的ViewPager发生滚动<br>
`解决方案：使用SDViewPager.addPullCondition(pullCondition)方法来给外部的ViewPager设置一个拖动条件限制，当拖动的触摸点在内部ViewPager可见范围之内的时候返回false不允许拖动`
  
## SDGridViewPager
需求：实现类似微信表情这种每一页都是一个网格布局<br>
![](http://thumbsnap.com/s/UE9uaWoJ.png?0810)<br>

正常情况下的实现步骤：
1. 准备好总的表情实体集合比如List\<ImageModel\> listModel
2. 新建一个ViewPager需要用到的适配器ImagePagerAdapter，继承自android.support.v4.view.PagerAdapter
3. 新建一个每一页中GridView需要用到的适配器ImageItemAdapter，继承自android.widget.BaseAdapter
4. 给ViewPager设置ImagePagerAdapter对象

在上述步骤总比较繁琐的地方是第2步中的适配器内部绑定数据的时候需要根据每一页一共要展示几个数据，当前是第几页来计算出当前页需要从listModel中取实体的范围。SDGridViewPager就是为了解决上述繁琐的过程而实现的类<br>

用SDGridViewPager的实现步骤：
1. 准备好总的表情实体集合比如List\<ImageModel\> listModel
2. 新建一个每一页中需要用到的适配器ImageItemAdapter，继承自android.widget.BaseAdapter
3. 给SDGridViewPager设置ImageItemAdapter对象

SDGridViewPager还支持设置分割线，具体见源码

```java
mViewPager.setGridItemCountPerPage(9); //设置每一页要显示多少个数据
mViewPager.setGridColumnCountPerPage(3); //设置每一页有几列
mViewPager.setGridAdapter(mItemAdapter); //设置适配器，适配器继承自android.widget.BaseAdapter
```
## SDViewPagerPlayer
一个简单的让ViewPager轮播的类，内部会考虑触摸ViewPager的时候暂停轮播，手松开后继续轮播的逻辑<br>
```java
mPlayer.setViewPager(mViewPager); //给播放者设置要轮播的ViewPager对象
mPlayer.startPlay(2 * 1000); //每隔2秒切换一次
mPlayer.stopPlay(); //停止轮播
```
