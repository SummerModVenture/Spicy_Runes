attribute vec4 Position;

/*uniform mat4 ProjMat;
uniform vec2 InSize;
uniform vec2 OutSize;
*/
varying vec2 texCoord;
varying vec3 worldPos;
vec4 cs_position;
varying vec4 normal;

void main(){
    cs_position = gl_ModelViewMatrix * gl_Vertex;
    worldPos = cs_position.xyz;
    gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * gl_Vertex;
    //gl_TexCoord = vec2(Position.x, Position.y);
    gl_TexCoord[0] = gl_MultiTexCoord0;
    texCoord = gl_MultiTexCoord0.xy;

    //vec3 N = gl_Normal.xyz;

    normal = //gl_ModelViewMatrix * vec4(N, 0.0);
       //vec4(gl_NormalMatrix * gl_Normal, 0.0);
       vec4(gl_Normal, 0.0);
}
