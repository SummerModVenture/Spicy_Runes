//attribute vec4 FragCoord;
#define M_PI 3.1415926535897932384626433832795

uniform sampler2D texture1;
uniform float width;
uniform float height;
uniform vec3 sunVec;

varying vec3 worldPos;
varying vec2 texCoord;
varying vec4 normal;

void main()
{
    //float distToCamera = sqrt(dot(worldPos, worldPos));
    //float angleToCamera = asin(abs(dot(worldPos, normal.xyz) / (length(worldPos) * length(normal))));
    //gl_FragColor = vec4(min(mult, 1.0) * tex.x, min(mult, 1.0) * tex.y, min(mult, 1.0) * tex.z, 1.0);
    //float shade = sign(0.25 - abs(distToCamera - 2.0));
    vec4 tex = texture2D(texture1, texCoord);
    //float shade = max(0.0, normal.y);//sign(0.1 - abs(angleToCamera - 0.5));
    float shade = max(0.0, abs(dot(sunVec, normal.xyz)) / length(normal));
    //gl_FragColor = vec4(shade, shade, shade, 1.0);
    gl_FragColor = vec4(shade * tex.x, shade * tex.y, shade * tex.z, 1.0);
   // gl_FragColor = vec4(sunVec.x * tex.x, sunVec.y * tex.y, 1.0 * tex.z, 1.0);

}