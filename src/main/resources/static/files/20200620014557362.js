// Vertex shader program
var SOLID_VSHADER_SOURCE =
    'attribute vec4 a_Position;\n' +
    'attribute vec4 a_Color;\n' +
    'uniform mat4 solidFormMatrix;\n' +
    'uniform bool click_edit;\n' +
    'varying vec4 v_Color;\n' +
    'void main() {\n' +
    '  if(click_edit) {\n' +
    '    gl_Position = a_Position;\n' +
    '  } else {\n' +
    '    gl_Position = solidFormMatrix * a_Position;\n' +
    '  }\n' +
    '  v_Color = a_Color;\n' +
    '}\n';

// Fragment shader program
var SOLID_FSHADER_SOURCE =
    'precision mediump float;\n' +
    'varying vec4 v_Color;\n' +
    'void main() {\n' +
    '  gl_FragColor = v_Color;\n' +
    '}\n';

// Vertex shader program
var BORDER_VSHADER_SOURCE =
    'attribute vec4 a_Position;\n' +
    'uniform mat4 borderFormMatrix;\n' +
    'uniform bool click_show_border;\n' +
    'void main() {\n' +
    '  if(click_show_border) {\n' +
    '    gl_Position = borderFormMatrix * a_Position;\n' +
    '  } else {\n' +
    '    gl_Position = vec4(0.0, 0.0, 0.0, 1.0);\n' +
    '  }\n' +
    '}\n';

// Fragment shader program
var BORDER_FSHADER_SOURCE =
    'precision mediump float;\n' +
    'void main() {\n' +
    '  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);\n;' +
    '}\n';

// Retrieve <canvas> element
var canvas = document.getElementById('webgl');

canvas.width = canvasSize.maxX;
canvas.height = canvasSize.maxY;

function main() {
    // Get the rendering context for WebGL
    var gl = getWebGLContext(canvas);
    if (!gl) {
        console.log('Failed to get the rendering context for WebGL');
        return;
    }

    // program for drawing triangles
    var solidProgram = createProgram(gl, SOLID_VSHADER_SOURCE, SOLID_FSHADER_SOURCE);
    // program for drawing borders
    var borderProgram = createProgram(gl, BORDER_VSHADER_SOURCE, BORDER_FSHADER_SOURCE);
    if (!solidProgram || !borderProgram) {
        console.log('Failed to intialize shaders.');
        return;
    }

    // Get storage locations of attribute and uniform variables in program object for multiple color drawing
    solidProgram.a_Position = gl.getAttribLocation(solidProgram, 'a_Position');
    solidProgram.a_Color = gl.getAttribLocation(solidProgram, 'a_Color');
    solidProgram.solidFormMatrix = gl.getUniformLocation(solidProgram, 'solidFormMatrix');
    solidProgram.click_edit = gl.getUniformLocation(solidProgram, 'click_edit');

    // Get storage locations of attribute and uniform variables in program object for border drawing
    borderProgram.a_Position = gl.getAttribLocation(borderProgram, 'a_Position');
    borderProgram.borderFormMatrix = gl.getUniformLocation(borderProgram, 'borderFormMatrix');
    borderProgram.click_show_border = gl.getUniformLocation(borderProgram, 'click_show_border');

    // draw borders
    gl.useProgram(borderProgram);
    gl.uniform1i(borderProgram.click_show_border, 1);

    var border = initBorders(gl, borderProgram);
    if(!border) {
        console.log('Failed to set the border information');
        return;
    }

    var triangles = initVertexBuffers(gl, solidProgram);
    if(!triangles) {
        console.log('Failed to set the vertex information');
        return;
    }

    // 注册mousedown事件处理函数
    canvas.onmousedown = function (event) {
        console.log("gggg");
        var e = window.event;
        var x = e.offsetX;
        var y = e.offsetY;

        // 判断点是否在某个顶点的周围一定位置
        for(var i = 0; i < points.length; i++) {
            if(isAround(x, y, points[i], tolerance) && isEditing) {
                flag = 1;
                dragPoint = i;
                break;
            }
        }
    };
    // 注册mousemove事件处理函数
    canvas.onmousemove = function (event) {
        var e = window.event;
        var newX = e.offsetX;
        var newY = e.offsetY;
        // 如果点击位置不在canvas中，则忽略事件
        if(newX < 0 || newX > canvasSize.maxX || newY < 0 || newY > canvasSize.maxY) {
            flag = 0;
        }

        // 当鼠标移动时，根据鼠标实时的坐标，重新进行绘制
        if(flag === 1 && dragPoint !== undefined) {
            points[dragPoint].x = newX;
            points[dragPoint].y = newY;

            border = initBorders(gl, borderProgram);
            triangles = initVertexBuffers(gl, solidProgram);
        }
    };
    // 注册mouseup事件处理函数
    canvas.onmouseup = function (event) {
        flag = 0;
    };

    // Set the clear color and enable the depth test
    gl.enable(gl.DEPTH_TEST);
    gl.clearColor(0.0, 0.0, 0.0, 1.0);

    // Calculate the view projection matrix
    var solidMatrix = new Matrix4();
    var borderMatrix = new Matrix4();

    // starting drawing
    var currentAngle = 0.0;
    var currentScale = 1.0;

    var tick = function () {
        var currentParams = animate(currentAngle, currentScale);

        // 实现动画的暂停与播放，控制下一次动画的angle和scale
        // 如果按下动画暂停键，那么将下一次的angle和scale设置和上一次一样，那么就达到了暂停的效果
        if(playAnimation) {
            currentAngle = currentParams[0];
            currentScale = currentParams[1];
        } else {
            // do nothing
        }

        gl.clear(gl.COLOR_BUFFER_BIT || gl.DEPTH_BUFFER_BIT);


        // draw borders
        drawBorders(gl, borderProgram, border, currentAngle, currentScale, borderMatrix);
        // draw triangles
        drawTriangles(gl, solidProgram, triangles, currentAngle, currentScale, solidMatrix);

        // 注册键盘响应函数
        document.onkeypress = function(event) {
            // console.log(event.keyCode);

            // press B
            if(event.keyCode == 98) {
                gl.useProgram(borderProgram);
                if(showBorders) {
                    gl.uniform1i(borderProgram.click_show_border, 0);
                    showBorders = false;
                } else {
                    gl.uniform1i(borderProgram.click_show_border, 1);
                    showBorders = true;
                }
            }

            // press T
            if(event.keyCode == 116) {
                // 任何时候按下 T ，都退出编辑模式
                isEditing = false;
                gl.uniform1i(solidProgram.click_edit, 0);

                if(playAnimation) {
                    playAnimation = false;
                } else {
                    playAnimation = true;
                }

            }

            // press E
            if(event.keyCode == 101) {
                // 按下编辑模式后，图形恢复原大小，下一次动画将从原位置开始
                currentAngle = 0.0;
                currentScale = 1.0;
                gl.useProgram(solidProgram);
                if(!playAnimation) {
                    isEditing = true;
                    gl.uniform1i(solidProgram.click_edit, 1);
                }
                if(playAnimation && !isEditing) {
                    playAnimation = false;
                    gl.uniform1i(solidProgram.click_edit, 1);
                    isEditing = true;
                }
            }

        };

        window.requestAnimationFrame(tick, canvas);
    };

    tick();
}

function initVertexBuffers(gl, solidProgram) {
    gl.useProgram(solidProgram);
    // the number of vertices
    var n = triangleVertices.length * 3;

    // the vertices
    var verticesArr = [];
    for(var j = 0; j < triangleVertices.length; j++) {
        for(var i = 0; i < triangleVertices[0].length; i++) {
            verticesArr.push(points[triangleVertices[j][i]].newX());
            verticesArr.push(points[triangleVertices[j][i]].newY());
            var color = points[triangleVertices[j][i]].color;
            verticesArr.push(color[0]);
            verticesArr.push(color[1]);
            verticesArr.push(color[2]);
        }
    }
    var vertices = new Float32Array(verticesArr);

    // create a buffer object
    var vertexBuffer = gl.createBuffer();
    if(!vertexBuffer) {
        console.log("Fail to create the buffer object.");
        return -1;
    }

    // bind the buffer object to the target
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);
    // write data to the buffer object
    gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW);

    var FSIZE = vertices.BYTES_PER_ELEMENT;

    // assign the buffer object to a_Position variable
    gl.vertexAttribPointer(solidProgram.a_Position, 2, gl.FLOAT, false, FSIZE * 5, 0);

    // enable the assignment to a_Position variable
    gl.enableVertexAttribArray(solidProgram.a_Position);

    // assign the buffer object to a_Color variable
    gl.vertexAttribPointer(solidProgram.a_Color, 3, gl.FLOAT, false,  FSIZE * 5, FSIZE * 2);

    // enable the assignment to a_Color variable
    gl.enableVertexAttribArray(solidProgram.a_Color);

    // unbind the buffer object
    gl.bindBuffer(gl.ARRAY_BUFFER, null);

    return n;
}

function initBorders(gl, borderProgram) {
    gl.useProgram(borderProgram);
    // the number of vertices
    var n = triangleVertices.length * 6;

    // the vertices
    var verticesArr = [];
    for(var j = 0; j < triangleVertices.length; j++) {
        for(var i = 0; i < triangleVertices[0].length; i++) {
            verticesArr.push(points[triangleVertices[j][i]].newX());
            verticesArr.push(points[triangleVertices[j][i]].newY());
            if(i + 1 < 3) {
                verticesArr.push(points[triangleVertices[j][i + 1]].newX());
                verticesArr.push(points[triangleVertices[j][i + 1]].newY());
            } else {
                verticesArr.push(points[triangleVertices[j][0]].newX());
                verticesArr.push(points[triangleVertices[j][0]].newY());
            }
        }
    }

    var vertices = new Float32Array(verticesArr);

    // Create a buffer object
    var vertexBuffer = gl.createBuffer();
    if (!vertexBuffer) {
        console.log('Failed to create the buffer object');
        return -1;
    }

    // Bind the buffer object to target
    gl.bindBuffer(gl.ARRAY_BUFFER, vertexBuffer);
    // Write date into the buffer object
    gl.bufferData(gl.ARRAY_BUFFER, vertices, gl.STATIC_DRAW);

    // Assign the buffer object to a_Position variable
    gl.vertexAttribPointer(borderProgram.a_Position, 2, gl.FLOAT, false, 0, 0);

    // Enable the assignment to a_Position variable
    gl.enableVertexAttribArray(borderProgram.a_Position);

    gl.bindBuffer(gl.ARRAY_BUFFER, null);

    return n;
}

function drawTriangles(gl, program, n, angle, scale, solidMatrix) {
    gl.useProgram(program);

    // 设置下一帧动画的旋转角度和缩放比例
    solidMatrix.setRotate(angle, 0, 0, 1);
    solidMatrix.scale(scale, scale, 1);

    gl.uniformMatrix4fv(program.solidFormMatrix, false, solidMatrix.elements);

    gl.drawArrays(gl.TRIANGLES, 0, n);
}

function drawBorders(gl, program, n, angle, scale, borderMatrix) {
    gl.useProgram(program);

    // 设置下一帧动画的旋转角度和缩放比例
    borderMatrix.setRotate(angle, 0, 0, 1);
    borderMatrix.scale(scale, scale, 1);

    gl.uniformMatrix4fv(program.borderFormMatrix, false, borderMatrix.elements);

    gl.drawArrays(gl.LINES, 0, n);
}

var g_last = Date.now();
// 标记scale是增大还是减小
var scaleDecreasing = true;
function animate(angle, scale) {
    var now = Date.now();
    var elapsed = now - g_last;

    // 去掉多余的周期数
    elapsed %= 8000;

    g_last = now;
    // 计算新的旋转角
    var newAngle = angle + (ANGLE_STEP * elapsed) / 1000.0;

    var newScale;
    var step = SCALE_STEP / 1000;
    var flag = 0.0;
    if (scaleDecreasing) {
        flag = scale - elapsed * step - MIN_SCALE;
        if(flag > 0.8) {
            scale = 1 - (scale - MIN_SCALE);
            scaleDecreasing = !scaleDecreasing;
            flag -= 0.8;
        }

        if (flag > 0) {
            newScale = scale - elapsed * step;
        } else {
            newScale = MIN_SCALE - flag;
            scaleDecreasing = false;
        }
    } else {
        flag = 1 - scale - elapsed * step;
        if(flag > 0.8) {
            scale = 1 - (scale - MIN_SCALE);
            scaleDecreasing = !scaleDecreasing;
            flag -= 0.8;
        }

        if (flag > 0) {
            newScale = scale + elapsed * step;
        } else {
            newScale = 1 + flag;
            scaleDecreasing = true;
        }
    }

    return [newAngle, newScale];
}


// 判断鼠标点击位置是否在某点周围
function isAround(x, y, point, radius) {
    var x1 = point.x;
    var y1 = point.y;
    return Math.pow(x - x1, 2) + Math.pow(y - y1, 2) <= Math.pow(radius, 2);
}

// 点击事件的flag
var flag = 0;
// 被拖拽的顶点的index
var dragPoint;
// 拖拽顶点的容差范围
var tolerance = 10;
// 所有顶点的集合
var points = [];
// 是否显示边框
var showBorders = true;
// 是否播放动画
var playAnimation = false;
// 是否处于编辑模式
var isEditing = true;
// degree per second
var ANGLE_STEP = 45.0;
// scale per second
var SCALE_STEP = 0.2;
// minimum scale
var MIN_SCALE = 0.2;

// 读取配置文件，获得所有的point对象
for(var i = 0; i < vertex_pos.length; i++) {
    var x = vertex_pos[i][0];
    var y = vertex_pos[i][1];
    var colorR = vertex_color[i][0] / 255;
    var colorG = vertex_color[i][1] / 255;
    var colorB = vertex_color[i][2] / 255;

    var point = new Point(x, y, [colorR, colorG, colorB]);
    points.push(point);
}


// 读取配置文件，获得所有的三角网格
var triangleVertices = [];
// for(var i = 0; i < polygon.length; i++) {
//     triangleVertices.push([polygon[i][3], polygon[i][0], polygon[i][2]]);
//     triangleVertices.push([polygon[i][2], polygon[i][0], polygon[i][1]]);
//     triangleVertices.push([polygon[i][0], polygon[i][1], polygon[i][2]]);
//     triangleVertices.push([polygon[i][0], polygon[i][2], polygon[i][3]]);
//
//     triangleVertices.push([polygon[i][1], polygon[i][2], polygon[i][3]]);
//     triangleVertices.push([polygon[i][2], polygon[i][3], polygon[i][0]]);
// }


triangleVertices.push([polygon[3][0], polygon[3][3], polygon[3][2]]);
triangleVertices.push([polygon[3][0], polygon[3][1], polygon[3][2]]);
triangleVertices.push([polygon[3][0], polygon[3][2], polygon[3][1]]);
triangleVertices.push([polygon[3][0], polygon[3][2], polygon[3][3]]);

triangleVertices.push([polygon[1][0], polygon[1][3], polygon[1][2]]);
triangleVertices.push([polygon[1][0], polygon[1][1], polygon[1][2]]);
triangleVertices.push([polygon[1][0], polygon[1][2], polygon[1][1]]);
triangleVertices.push([polygon[1][0], polygon[1][2], polygon[1][3]]);


triangleVertices.push([polygon[2][0], polygon[2][3], polygon[2][2]]);
triangleVertices.push([polygon[2][0], polygon[2][1], polygon[2][2]]);
triangleVertices.push([polygon[2][0], polygon[2][2], polygon[2][1]]);
triangleVertices.push([polygon[2][0], polygon[2][2], polygon[2][3]]);

triangleVertices.push([polygon[0][0], polygon[0][3], polygon[0][2]]);
triangleVertices.push([polygon[0][0], polygon[0][1], polygon[0][2]]);
triangleVertices.push([polygon[0][0], polygon[0][2], polygon[0][1]]);
triangleVertices.push([polygon[0][0], polygon[0][2], polygon[0][3]]);
//
// triangleVertices.push([polygon[2][1], polygon[2][2], polygon[2][3]]);
// triangleVertices.push([polygon[2][2], polygon[2][3], polygon[2][0]]);
// triangleVertices.push([polygon[3][1], polygon[3][2], polygon[3][3]]);
// triangleVertices.push([polygon[3][2], polygon[3][3], polygon[3][0]]);

// for(var i = 0; i < triangleVertices.length; i++) {
//     console.log(triangleVertices[i]);
// }

//main();

