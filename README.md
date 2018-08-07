# Day1
## 创建OpenGL应用的主要步骤：
1. 获取系统支持的OpenGLES的版本
2. 为OpenGL ES2.0配置渲染表面（rendering surface）
3. 处理Android Activity生命周期事件
4. 创建渲染Render类

### 1.获取系统支持的OpenGLES的版本
```java
private boolean isSupportOpenGLES2() {
    final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
    boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
    return supportsEs2;
}
```
### 2.为OpenGL ES2.0配置渲染表面（rendering surface）
```java
if(supportEs2){
    //Request an OpenGL ES 2.0 compatible context.
    //Inform the default EGLContextFactory and default EGLConfigChooser which EGLContext client version to pick. 
    //告诉默认的EGLContextFactory和默认的EGLConfigChooser选择哪个版本的EGLContext client
    glSurfaceView.setEGLContextClientVersion(2);

    //Assign our renderer
    //设置渲染器Render
    glSurfaceView.setRender(new FirstOpenGLProjectRender());

    rendererSet = true;
}else{
    return;
}
```

### 3.处理Android Activity生命周期事件
当Activity显示隐藏时需要处理GLSurfaceView，否则应用就会崩溃。
在步骤2中设置完渲染器后把```rendererSet```设置为true，用来表明GLSurfaceView已经设置完成。
接下来就需要处理Activity生命周期相关的方法。
```java
@override
protected void onPause(){
    super.onPause();
    if(rendererSet){
        glSurfaceView.onPause();//暂停后台渲染线程
    }
}

@override
protected void onResume(){
    super.onResume();
    if(rendererSet){
        glSurfaceView.onResume();//继续后台渲染线程
    }
}
```
### 4.创建渲染Render类
现在要定义一个渲染器，以便开始清空屏幕
定义渲染器Render需要实现Render接口：
1. onSurfaceCreated(GL10 glUnused,EGLConfig config);
当Surface被创建的时候，GLSurfaceView会调用这个方法
2. onSurfaceChanged(GL10 glUnused,int width,int height);
在Surface被创建后，每次surface尺寸变化时，这个方法都会被GLSurfaceView调用。（横竖屏切换，大小变化等）
3. onDrawFrame(GL10 glUnused);
当绘制一帧时，这个方法会被GLSufraceView调用。在这个方法中，我们一定要绘制一些东西，即使只是清空屏幕。因为在这个方法返回后，渲染缓冲区会被交换并显示在屏幕上，如果什么都没画，可能会看到糟糕的闪烁的效果。  

上面参数中GL10，是OpenGL ES1.0的API遗留下来的，如果要编写使用OpenGL ES1.0的渲染器，就要使用这个参数。但是，**对于OpenGL ES2.0，GLES20类提供了静态方法来存取**。

**<font color='red'>在后台线程中渲染</font>**  
GLSurfaceView会在一个单独的线程中调用渲染器的方法。默认情况下，GLSurfaceView会议显示设备的刷新频率不断地渲染，当然，它也可以配置为按请求渲染，只需要用GLSurfaceView.RENDERMODE_WHEN_DIRTY作为参数调用GLSurfaceView.setRenderMode()即可。  
既然Android的GLSurfaceView在后台线程中执行渲染，就必须要小心，只能在这个渲染线程中调用OpenGL，在Android主线程中使用UI相关的调用；两个线程之间的通信可以用如下方法：  
在主线程中的GLSurfaceView实例可以调用queueEvent()方法传递一个Runnable给后台渲染线程，渲染线程可以调用Activity的runOnUIThread()来传递事件给主线程。

```java
public class FirstOpenGLProjectRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //设置清空屏幕用的颜色，当屏幕被清空时，就会填充红色
        //前三个参数分别对应红色、绿色、蓝色，最后一个参数为alpha，用来表示般透明度或透明度
        //颜色范围在0-1之间：1最大，0最小
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置视口(viewport)尺寸，告诉OpenGL可以用来渲染的surface的大小
        //set the OpenGL viewport to fill the entire surface
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //clear the rendering surface
        //清空屏幕，这会擦出屏幕上的所有颜色，并用之前glClearColor()调用定义的颜色填充整个屏幕
        glClear(GL_COLOR_BUFFER_BIT);
    }
}

```