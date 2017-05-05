import pymysql

def CreateConnection():
    try:
        conn = pymysql.connect(host='localhost', user='root', password='root', db='exhibition', cursorclass=pymysql.cursors.DictCursor)
        return conn

    except Exception as e:
        print e
        return e.message


def CloseConnection(conn):
    try:
        conn.close()

    except Exception as e:
        print e


def addFavGallery(artist_id,gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_addFavoriteGallery', (artist_id,gallery_id))

        conn.commit()

        result = cursor.fetchall()

        CloseConnection(conn)

        return  result
    except Exception as e:
        print e
        return e.message



def deleteFavGallery(artist_id,gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_dropFavoriteGallery', (artist_id,gallery_id))

        conn.commit()

        result = cursor.fetchall()

        CloseConnection(conn)

        return  result
    except Exception as e:
        print e
        return e.message


def get_gallery_list(artist_id):
    try:

        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getGalleryList', (artist_id,))

        result = cursor.fetchall()
        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message


def add_new_gallery(gallery_name, year, desc, photoBlob, latit, longit):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_insertGallery', (gallery_name, year, desc, photoBlob, latit, longit))

        conn.commit()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e


def get_gallery_details(gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getGalleryDetails', (gallery_id,))

        result = {'gallery_detail':[], 'gallery_artist':[], 'gallery_art': []}

        result_headers = ['gallery_detail', 'gallery_artist', 'gallery_art']

        for index, dataset in enumerate(cursor):
            if len(cursor._rows) == 1 and index == 0:
                rows_set = cursor._rows[0]
            else:
                rows_set = cursor._rows
            result[result_headers[index]] = rows_set
            #test = cursor.fetchall()
            cursor.nextset()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message


def get_artist_details(artist_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getArtistDetails', (artist_id,))

        result = {'artist_detail': '', 'artworks': ''}

        result_headers = ['artist_detail', 'artworks']

        for index, dataset in enumerate(cursor):
            if len(cursor._rows) == 1 and index == 0:
                rows_set = cursor._rows[0]
            else:
                rows_set = cursor._rows
            result[result_headers[index]] = rows_set
            # test = cursor.fetchall()
            cursor.nextset()

        CloseConnection(conn)

        return result
    except Exception as e:
        print e
        return e.message


def get_art_details(art_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_getArtDetails', (art_id,))

        result = cursor.fetchall()

        CloseConnection(conn)

        return result
    except Exception as e:
        print e
        return e.message


def add_new_artist(artist_name, description, file_name):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_insertArtist', (artist_name, description, file_name))

        conn.commit()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message


def upload_artwork(art_name, desc, artist_id, file_name, gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_uploadArt', (art_name, desc, artist_id, file_name, gallery_id))

        conn.commit()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message


def deleteArt(art_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        result = cursor.callproc('sp_deleteArt', (art_id,))

        conn.commit()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message


def addSubmission(art_id, artist_id, gallery_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_addSubmission', (art_id, artist_id, gallery_id))

        conn.commit()

        result = cursor.fetchone()

        CloseConnection(conn)

        return  result
    except Exception as e:
        print e
        return e.message


def addArtTrait(art_id, trait):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_addArtTrait', (art_id, trait))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message


def updateArTrait(art_id, trait_id, new_trait):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_updateArtTrait', (art_id,trait_id,new_trait))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message


def deleteArtTrait(art_id, trait_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_deleteArtTrait', (trait_id,art_id))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message



def addArtistTrait(artist_id, trait):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_addArtistTrait', (artist_id, trait))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message


def updateArtistTrait(artist_id, trait_id, new_trait):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_updateArtistTrait', (artist_id,trait_id,new_trait))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message


def deleteArtistTrait(artist_id, trait_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_deleteArtistTrait', (trait_id,artist_id))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message



def addGalleryTrait(gallery_id, trait):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_addGalleryTrait', (gallery_id, trait))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message



def updateGalleryTrait(gallery_id, trait_id, new_trait):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_updateGalleryTrait', (gallery_id,trait_id,new_trait))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message



def deleteGalleryTrait(gallery_id, trait_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_deleteGalleryTrait', (trait_id,gallery_id))

        conn.commit()

        result = cursor.fetchone()

        return result

    except Exception as e:
        print e
        return e.message



def search(search_term,artist_id):
    try:
        conn = CreateConnection()

        cursor = conn.cursor()

        cursor.callproc('sp_search', (search_term,artist_id))

        result = {'gallery_list':[], 'artist_list':[], 'art_list': []}

        result_headers = ['gallery_list', 'artist_list', 'art_list']

        for header in result_headers:
            if len(cursor._rows) == 1:
                rows_set = cursor._rows[0]
            else:
                rows_set = cursor._rows

            result[header] = rows_set
            cursor.nextset()

        CloseConnection(conn)

        return result

    except Exception as e:
        print e
        return e.message



def searchTrait(search_term):
    try:

        conn = CreateConnection()

        cursor = conn.cursor()

        result = {'gallery_list': [], 'artist_list': [], 'art_list': []}

        result_headers = ['gallery_list', 'artist_list', 'art_list']

        cursor.callproc('sp_searchTraitGallery', (search_term,))

        result['gallery_list'] = cursor.fetchall()

        cursor.callproc('sp_searchTraitArtist', (search_term,))

        result['artist_list'] = cursor.fetchall()

        cursor.callproc('sp_searchTraitArt', (search_term,))

        result['art_list'] = cursor.fetchall()

        CloseConnection(conn)

        return result
    except Exception as e:
        print e
        return e.message



def getSubmissions(gallery_id):
    try:

        conn = CreateConnection()

        cursor = conn.cursor()


        cursor.callproc('sp_getSubmissions', (gallery_id,))


        result = cursor.fetchall()

        CloseConnection(conn)

        return result
    except Exception as e:
        print e
        return e.message