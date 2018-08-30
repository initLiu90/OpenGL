precision mediump float;

varying vec2 v_TextureCoordinates;//纹理坐标
uniform sampler2D u_TextureUnit;//接受实际的纹理坐标，sample2D这个变量类型指的是一个二维纹理数据的数组

void main(){
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates);
}