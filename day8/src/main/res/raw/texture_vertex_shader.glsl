attribute vec4 a_Position;//桌子顶点(x,y)
attribute vec2 a_TextureCoordinate;//桌子的纹理坐标（s,t）

uniform mat4 u_Matrix;//投影matrix

varying vec2 v_TextureCoordinate;

void main(){
    gl_Position = u_Matrix * a_Position;//桌子原始顶点坐标向量和matrix矩阵 相乘
    v_TextureCoordinate = a_TextureCoordinate;//把纹理坐标，传递给顶点着色器被插值的varying
}