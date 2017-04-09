from flask import Flask, send_file, request, send_from_directory, url_for, redirect, jsonify,render_template
import io
import os
import base64
import SQLConnection
import json
import time

app = Flask(__name__)
app.config['UPLOAD_FOLDER'] = 'UPLOADS'
# Temp secret key, make random, basically session key
app.secret_key = os.urandom(32)


# conn = pymysql.connect(host = 'localhost',
#                       user= 'root',
#                       password = 'root',
#                       db = 'findfolks',
#                       charset = 'utf8mb4',
#                       cursorclass = pymysql.cursors.DictCursor)

@app.route("/")
def home():
    '''
    img = open('UPLOADS/30dayAbs.jpg', 'rb')
    img_bytes = img.read()
    file_name = '30daysAbs.jpg'
    file_name_arr = file_name.split('.')
    curr_time = int(time.time())

    new_file_name = '{}_{}.{}'.format(file_name_arr[0],curr_time,file_name_arr[-1])

    f = open('UPLOADS/'+new_file_name, 'wb')
    f.write(img_bytes)
    f.close()
    return 'You have reached Exhibitions API '
    '''
    return render_template('index.html')

@app.route("/getGalleryList")
def getArtistList():
    try:
        artist_id = int(request.args['artist_id'])

        if artist_id is None:
            return jsonify('Error: Artist ID required')

        result = SQLConnection.get_gallery_list(artist_id)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route("/addNewGallery")
def addNewGallery():
    try:
        gallery_name = request.args['gallery_name']
        year = request.args['year']
        desc = request.args['desc']
        photoBlob = request.args['photoBlob']
        latit = request.args['latit']
        longit = request.args['longit']

        result = SQLConnection.add_new_gallery(gallery_name, year, desc, photoBlob, latit, longit)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route("/getGalleryDetail")
def getGalleryDetail():
    try:
        gallery_id = request.args['gallery_id']

        result = SQLConnection.get_gallery_details(gallery_id)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route("/addNewArtist")
def addNewArtist():
    try:
        artist_name = request.args['artist_name']
        description = request.args['desc']
        img_bytes = request.args['img_bytes']
        file_name = request.args['file_name']

        file_name_arr = file_name.split('.')
        curr_time = int(time.time())

        new_file_name = '{}_{}.{}'.format(file_name_arr[0], curr_time, file_name_arr[-1])

        f = open('UPLOADS/' + new_file_name, 'wb')
        f.write(img_bytes)
        f.close()

        result = SQLConnection.add_new_artist(artist_name,description,new_file_name)




    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/images/<path:filename>')
def images(filename):
    try:
        return send_from_directory(os.path.join('UPLOADS/'), filename)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return  jsonify(error)



if __name__ == "__main__":
    app.run('127.0.0.1', 5000, debug=True)


