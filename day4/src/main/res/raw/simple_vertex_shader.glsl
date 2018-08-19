attribute vec4 a_Position;//点的位置

void main(){
    gl_Position = a_Position;
    gl_PointSize = 10.0;
}