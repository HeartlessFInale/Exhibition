from flask import Flask, send_file, request, send_from_directory, url_for, redirect, jsonify, render_template
import io
import os
import base64
import SQLConnection
import json
import time

import smtplib
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart

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


@app.route("/addFavGallery")
def addfavgallery():
    try:
        artist_id = request.args['artist_id']
        gallery_id = request.args['gallery_id']

        result = SQLConnection.addFavGallery(artist_id,gallery_id)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route("/deleteFavGallery")
def deletefavgallery():
    try:
        artist_id = request.args['artist_id']
        gallery_id = request.args['gallery_id']

        result = SQLConnection.deleteFavGallery(artist_id, gallery_id)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)

@app.route("/getGalleryList")
def getGalleryList():
    try:
        artist_id = int(request.args['artist_id'])

        if artist_id is None:
            return jsonify('Error: Artist ID required')

        result = SQLConnection.get_gallery_list(artist_id)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route("/addNewGallery" , methods = ['POST'])
def addNewGallery():
    try:
        gallery_name = request.args['gallery_name']
        year = request.args['year']
        desc = request.args['desc']

        latit = request.args['latit']
        longit = request.args['longit']

        img_bytes = request.data
        file_name = request.args['file_name']

        file_name_arr = file_name.split('.')
        curr_time = int(time.time())

        new_file_name = '{}_{}.{}'.format(
            file_name_arr[0], curr_time, file_name_arr[-1])

        f = open('UPLOADS/' + new_file_name, 'wb')
        f.write(img_bytes)
        f.close()


        result = SQLConnection.add_new_gallery(
            gallery_name, year, desc, new_file_name, latit, longit)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)

@app.route("/getArtDetail")
def getArtDetail():
    try:
        art_id = request.args['art_id']

        result = SQLConnection.get_art_details(art_id)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)

@app.route("/getArtistDetail")
def getArtistDetail():
    try:
        artist_id = request.args['artist_id']

        result = SQLConnection.get_artist_details(artist_id)

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


@app.route("/addNewArtist", methods = ['POST'])
def addNewArtist():
    try:
        artist_name = request.args['artist_name']
        description = request.args['desc']

        img_bytes = request.data

        file_name = request.args['file_name']

        file_name_arr = file_name.split('.')
        curr_time = int(time.time())

        new_file_name = '{}_{}.{}'.format(
            file_name_arr[0], curr_time, file_name_arr[-1])

        f = open('UPLOADS/' + new_file_name, 'wb')
        f.write(img_bytes)
        f.close()

        result = SQLConnection.add_new_artist(
            artist_name, description, new_file_name)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/uploadArtwork', methods=['POST'])
def uploadArtwork():
    try:
        art_name = request.args['art_name']
        description = request.args['desc']
        artist_id = request.args['artist_id']
        if 'gallery_id' in request.args:
            gallery_id = request.args['gallery_id']
        else:
            gallery_id = None

        img_bytes = request.data
        file_name = request.args['file_name']

        '''
        art_name = 'Test Artwork'
        description = 'This is Test Artwork Upload'
        artist_id = 1
        gallery_id = 1


        img = open('UPLOADS/30dayAbs.jpg', 'rb')
        img_bytes = img.read()
        file_name = '30daysAbs.jpg'
        '''

        file_name_arr = file_name.split('.')
        curr_time = int(time.time())

        new_file_name = '{}_{}.{}'.format(
            file_name_arr[0], curr_time, file_name_arr[-1])

        f = open('UPLOADS/' + new_file_name, 'wb')
        f.write(img_bytes)
        f.close()

        result = SQLConnection.upload_artwork(
            art_name, description, artist_id, new_file_name, gallery_id)

        return jsonify('Successfully Uploaded Art')

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/deleteArt')
def deleteArt():
    try:
        art_id = request.args['art_id']

        result = SQLConnection.deleteArt(art_id)

        return jsonify('Successfully Deleted Art')

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/images/<path:filename>')
def images(filename):
    try:
        return send_from_directory(os.path.join('UPLOADS/'), filename)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/addSubmission')
def addSubmission():
    try:
        art_id = request.args['art_id']
        artist_id = request.args['artist_id']
        gallery_id = request.args['gallery_id']

        result = SQLConnection.addSubmission(art_id, artist_id, gallery_id)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/addArtTrait')
def addArtTrait():
    try:
        art_id = request.args['art_id']
        trait = request.args['trait']

        result = SQLConnection.addArtTrait(art_id, trait)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/updateArtTrait')
def updateArtTrait():
    try:
        art_id = request.args['art_id']
        trait_id = request.args['trait_id']
        new_trait = request.args['new_trait']

        result = SQLConnection.updateArtTrait(art_id, trait_id,new_trait)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)

@app.route('/deleteArtTrait')
def deleteArtTrait():
    try:
        art_id = request.args['art_id']
        trait_id = request.args['trait_id']

        result = SQLConnection.deleteArtTrait(art_id, trait_id)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/addArtistTrait')
def addArtistTrait():
    try:
        artist_id = request.args['artist_id']
        trait = request.args['trait']

        result = SQLConnection.addArtistTrait(artist_id, trait)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/updateArtistTrait')
def updateArtistTrait():
    try:
        artist_id = request.args['artist_id']
        trait_id = request.args['trait_id']
        new_trait = request.args['new_trait']

        result = SQLConnection.updateArtistTrait(artist_id, trait_id,new_trait)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/deleteArtistTrait')
def deleteArtistTrait():
    try:
        artist_id = request.args['artist_id']
        trait_id = request.args['trait_id']

        result = SQLConnection.deleteArtistTrait(artist_id, trait_id)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/addGalleryTrait')
def addGalleryTrait():
    try:
        gallery_id = request.args['gallery_id']
        trait = request.args['trait']

        result = SQLConnection.addGalleryTrait(gallery_id, trait)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/updateGalleryTrait')
def updateGalleryTrait():
    try:
        gallery_id = request.args['gallery_id']
        trait_id = request.args['trait_id']
        new_trait = request.args['new_trait']

        result = SQLConnection.updateGalleryTrait(gallery_id, trait_id,new_trait)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/deleteGalleryTrait')
def deleteGalleryTrait():
    try:
        gallery_id = request.args['gallery_id']
        trait_id = request.args['trait_id']

        result = SQLConnection.deleteGalleryTrait(gallery_id, trait_id)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/search')
def search():
    try:
        search_term = request.args['search_term']
        artist_id = request.args['artist_id']

        result = SQLConnection.search(search_term, artist_id)

        return jsonify(result)
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/searchTrait')
def searchTrait():
    try:
        search_term = request.args['search_term']

        search_term = search_term.replace(',', '|')

        result = SQLConnection.searchTrait(search_term)

        return jsonify(result)
        
    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/getSubmissions')
def getSubmissions():
    try:
        gallery_id = request.args['gallery_id']

        result = SQLConnection.getSubmissions(gallery_id)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/accept_reject_submission')
def accept_reject():
    try:
        submission_id = request.args['submission_id']

        is_accepted = bool(request.args['is_accepted'])

        reason = request.args['reason']

        result = SQLConnection.accept_reject(submission_id, is_accepted, reason)

        return jsonify(result)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)

@app.route('/reportArtwork')
def reportArtwork():
    try:
        art_id = request.args['art_id']
        reason = request.args['reason']

        report_id = 'Artwork Id : %s' %(art_id)

        return_code, status = send_report_email(report_id, reason)

        return jsonify(return_code=return_code,status=status)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/reportArtist')
def reportArtist():
    try:
        artist_id = request.args['artist_id']
        reason = request.args['reason']

        report_id = 'Artist Id : %s' %(artist_id)

        return_code, status = send_report_email(report_id, reason)

        return jsonify(return_code=return_code,status=status)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


@app.route('/reportGallery')
def reportGallery():
    try:
        gallery_id = request.args['gallery_id']
        reason = request.args['reason']

        report_id = 'Gallery Id : %s' %(gallery_id)

        return_code, status = send_report_email(report_id, reason)

        return jsonify(return_code=return_code,status=status)

    except Exception as e:
        error = 'Internal Server Error: {}'.format(e.message)
        return jsonify(error)


def send_report_email(report_id, report_reason):
    try:
        me = 'ts2911@nyu.edu'
        you = 'tejasshah@nyu.edu'

        email_content = """
        <html>
        <head>
        <style>
        .body{
            margin: 0 auto;
            background-color:#000;
            color:#fff;
        }
        .title{
            font-family:helvetica;
            font-size:1.5em;
            padding:5px;
            margin-left:15px;
        }
        .thumbnail{
            width:50px;
            height: 50px;
        }
        .content{
            margin-left:15px;
            font-size: 1.1em;
        }
        footer{
            margin:50px 15px;
            font-size:1.3em;
        }
        </style>
        </head>
        <body class="body">
        <div style="background-color:#3F51B5">
            <p class="title">Exhibitions</p>
        </div>
        <div class="content">
            <p>Report Filed For - %s</p>
            <p>Reason - <br> %s</p>
        </div>
        <footer>
            <img class="thumbnail" src="cid:image1" />
            <p>Thank you,<br>Team Exhibitors</p>
        </footer>
        </body>
        </html>
        """ % (report_id, report_reason)


        msg = MIMEMultipart('related')

        msg_alternative = MIMEMultipart('alternative')
        msg.attach(msg_alternative)


        msgText = MIMEText('Report Filed for %s.\n Reason: %s'.format(report_id,report_reason))
        msg_alternative.attach(msgText)

        msgText = MIMEText(email_content,'html')
        msg_alternative.attach(msgText)

        img_data = open('static/images/ExhibitionIcon.png', 'rb').read()

        image = MIMEImage(img_data)
        image.add_header('Content-ID','<image1>')

        msg.attach(image)

        # Send the message via our own SMTP server, but don't include the
        # envelope header.
        msg['Subject'] = 'Report for %s' % report_id
        msg['From'] = me
        msg['To'] = you
        msg.preamble = 'This is a multi-part message in MIME format.'

        username = 'tejasshah.nmims@gmail.com'
        password = ''
        s = smtplib.SMTP('smtp.gmail.com:587')
        s.starttls()
        s.login(username,password)
        s.sendmail(me, [you], msg.as_string())
        s.quit()

        return 1, 'Email Sent Successfully'

    except Exception as e:
        error = 'Error Sending Email: {}'.format(e.message)
        return -1, error

if __name__ == "__main__":
    app.run('127.0.0.1', 5000)
