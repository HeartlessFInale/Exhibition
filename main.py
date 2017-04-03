from flask import g, abort, Flask, render_template, request, session, url_for, redirect, jsonify
import os
import base64
import SQLConnection
import json

app = Flask(__name__)

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
    return 'You have reached Exhibitions API '


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


if __name__ == "__main__":
    app.run('127.0.0.1', 5000, debug=True)


