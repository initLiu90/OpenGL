uniform mat4 u_Matrix;
attribute vec4 a_Position;//x,y.z.w
attribute vec2 a_TextureCoordinates;//s,t
varying vec2 v_TextureCoordinates;

void main(){
    gl_Position = u_Matrix*a_Position;
    v_TextureCoordinates = a_TextureCoordinates;

}
