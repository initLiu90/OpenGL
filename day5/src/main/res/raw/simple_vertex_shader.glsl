uniform mat4 u_Matrix;//代表一个4*4的矩阵

attribute vec4 a_Position;
attribute vec4 a_Color;

varying vec4 v_Color;

void main(){
    v_Color = a_Color;

    gl_Position = u_Matrix * a_Position;//位置与矩阵的乘机
    gl_PointSize = 10.0;
}