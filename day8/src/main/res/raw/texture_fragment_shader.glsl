precision mediump float;

uniform sample2D u_TextureUnit;//片段着色器通过这个字段接受实际的纹理数据。sampler2D类型指的是一个二维纹理数据的数组
varying vec2 v_TextureCoordiante;//纹理坐标

void main(){
    gl_FragColor = texture2D(u_TextureUnit,v_TextureCoordiante);
}