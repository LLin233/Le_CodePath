#Some notes:

###optimization

1. Use hardware to accelerate app. 
add `android:hardwareAccelerated=”true”` after appliation tag. It's activated by default after version Android 4.0.

2. Set View cacheable. 
`setDrawingCache = "true"`

3. `<merge>` avoid over-drawing layout,  `<include>` shared layout.
4. Use files when you could do it. It's 10 times faster then doing SQL operation.
5. if no local member is invoked in a method, make it `static`, it will up performance by 15~20%.
6. Check memory limit per app.

       ` ActivityManager manager = (ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);`
        `Log.d("Memory", manager.getMemoryClass() + "");`

7. Why Viewholder? 

	ListView or RecyclerView could only call findbyId() limited times and then reuse the elements when list scrolled up or down.   

	Tip: declare Viewholder with key word `static`, and it will reduce the amount of `this` calls from `MainActivity`, and then reduce the amount of `MainActivity` reference calls.