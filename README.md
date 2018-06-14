# Gradle
```
implementation 'com.fanwe.android:viewpager:1.0.31'

// 依赖以下库
implementation 'com.android.support:support-v4:+'
```

# FViewPager
* 解决设置高度为wrap_content无效<br>

* 当ViewPager嵌套ViewPager的时候，有些情况下不希望内部的ViewPager滚动到边界的时候继续拖动导致外部的ViewPager发生滚动<br>
`解决方案：使用FViewPager.addPullCondition(pullCondition)方法来给外部的ViewPager设置一个拖动条件限制，当拖动的触摸点在内部ViewPager可见范围之内的时候返回false不允许拖动`
  
# FGridViewPager
```java
/**
 * 设置每页要显示的item数量
 */
mViewPager.setGridItemCountPerPage(9);
/**
 * 设置每页的数据要按几列展示
 */
mViewPager.setGridColumnCountPerPage(3);
/**
 * 设置横分割线
 */
mViewPager.setGridHorizontalDivider(getResources().getDrawable(R.drawable.divider_horizontal));
/**
 * 设置竖分割线
 */
mViewPager.setGridVerticalDivider(getResources().getDrawable(R.drawable.divider_vertical));
/**
 * 设置适配器，android.widget.BaseAdapter
 */
mViewPager.setGridAdapter(mAdapter);
```
