attribute vec4 a_Position;//点的位置
attribute vec4 a_Color;//顶点的颜色属性

varying vec4 v_Color;

void main(){
    v_Color = a_Color;//把顶点颜色进行混合，然后将混合后的值发送给片段着色器
    gl_Position = a_Position;
    gl_PointSize = 10.0;
}