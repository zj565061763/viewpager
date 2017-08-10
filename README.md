## SDViewPager
#### 问题1：在xml中给ViewPager设置高度为wrap_content的时候无法包裹内容

解决方案：使用SDViewPager

#### 问题2：当我们需要监听Adapter的数据发生变化的时候，给Adapter注册了一个DataSetObserver对象，那么当ViewPager被设置新的Adapter对象的时候，需要先对旧的Adapter取消注册DataSetObserver对象，再对新的对象注册DataSetObserver对象，处理比较繁琐

解决方案：使用SDViewPager.setDataSetObserver(dataSetObserver)方法来监听数据变化，内部会考虑Adapter发生变更的时候需要取消和需要注册的逻辑

#### 问题3：当我们需要给ViewPager添加滚动指示器来指示当前滚动到第几页，一共有几页。设置Adapter的时候或者Adapter的内容发生变化的时候总页数都可能发生变化，处理比较繁琐

解决方案：使用SDViewPager.setOnPageCountChangeCallback(onPageCountChangeCallback)方法来监听总页数发生变化

#### 问题4：当ViewPager嵌套ViewPager的时候，有些情况下不希望内部的ViewPager滚动到边界的时候继续拖动导致外部的ViewPager发生滚动

解决方案：使用SDViewPager.addPullCondition(pullCondition)方法来给外部的ViewPager设置一个拖动条件限制，当拖动的触摸点在内部ViewPager可见范围之内的时候返回false不允许拖动

## SDGridViewPager
需求：实现类似微信表情这种每一页都是一个网格布局<br>
![](http://thumbsnap.com/s/UE9uaWoJ.png?0810)

正常情况下的实现步骤：
1. 准备好总的表情实体集合比如List\<ImageModel\> listModel
2. 新建一个ViewPager需要用到的适配器ImagePagerAdapter
3. 新建一个每一页中GridView需要用到的适配器ImageItemAdapter
4. 给ViewPager设置ImagePagerAdapter对象



