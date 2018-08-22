precision mediump float;

uniform sampler2D u_TextureUnit;//片段着色器通过这个字段接受实际的纹理数据。sampler2D类型指的是一个二维纹理数据的数组
varying vec2 v_TextureCoordinates;//纹理坐标

void main(){
    //被插值的纹理坐标和纹理数据被传递给着色器函数texture2D，他会读入纹理中那个特定坐标出的颜色。
    gl_FragColor = texture2D(u_TextureUnit,v_TextureCoordinates);
}