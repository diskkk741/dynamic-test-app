from flask_cors import CORS
from flask_mysqldb import MySQL
import MySQLdb.cursors
from flask import Flask, jsonify, request

app = Flask(__name__)
CORS(app)

# Config
app.config.from_pyfile('config.py')
mysql = MySQL(app)

@app.route("/ping")
def ping():
    return "pong", 200

@app.route('/api/questions', methods=['GET'])
def get_questions():
    difficulty = request.args.get('difficulty')  # easy, medium, hard gibi parametre bekleniyor
    cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)

    if difficulty:
        cursor.execute("SELECT * FROM questions WHERE difficulty = %s ORDER BY RAND() LIMIT 1", (difficulty,))
    else:
        cursor.execute("SELECT * FROM questions ORDER BY RAND() LIMIT 1")

    questions = cursor.fetchall()
    cursor.close()
    return jsonify(questions)

@app.route('/api/test-result', methods=['POST'])
def save_test_result():
    data = request.get_json()
    user_id = data.get('user_id')
    score = data.get('score')
    total = data.get('total')

    if not all([user_id, score, total]):
        return jsonify({'error': 'Eksik veri'}), 400

    cursor = mysql.connection.cursor()
    cursor.execute("""
        INSERT INTO test_results (user_id, score, total)
        VALUES (%s, %s, %s)
    """, (user_id, score, total))
    mysql.connection.commit()

    result_id = cursor.lastrowid
    cursor.close()

    return jsonify({'message': 'Test sonucu kaydedildi', 'result_id': result_id}), 201

@app.route('/api/test-results/<int:user_id>', methods=['GET'])
def get_test_results(user_id):
    cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
    cursor.execute("SELECT * FROM test_results WHERE user_id = %s ORDER BY completed_at DESC", (user_id,))
    results = cursor.fetchall()
    cursor.close()
    return jsonify(results)

@app.route('/api/performance-log', methods=['POST'])
def save_performance_log():
    data = request.get_json()
    user_id = data.get('user_id')
    difficulty = data.get('difficulty')
    is_correct = data.get('is_correct')

    if not all([user_id, difficulty, isinstance(is_correct, bool)]):
        return jsonify({'error': 'Eksik veya hatalı veri'}), 400

    cursor = mysql.connection.cursor()
    cursor.execute("""
        INSERT INTO test_performance (user_id, difficulty, is_correct)
        VALUES (%s, %s, %s)
    """, (user_id, difficulty, is_correct))
    mysql.connection.commit()
    cursor.close()

    return jsonify({'message': 'Performans kaydı alındı'}), 201

@app.route('/api/test-analytics', methods=['POST'])
def post_test_analytics():
    data = request.get_json()
    result_id = data.get("result_id")
    correct_easy = data.get("correct_easy", 0)
    wrong_easy = data.get("wrong_easy", 0)
    correct_medium = data.get("correct_medium", 0)
    wrong_medium = data.get("wrong_medium", 0)
    correct_hard = data.get("correct_hard", 0)
    wrong_hard = data.get("wrong_hard", 0)
    total_time_sec = data.get("total_time_sec", 0)

    if not result_id:
        return jsonify({'msg': 'result_id eksik'}), 400

    try:
        cursor = mysql.connection.cursor()
        cursor.execute("""
            INSERT INTO test_analytics (
                result_id, correct_easy, wrong_easy,
                correct_medium, wrong_medium,
                correct_hard, wrong_hard, total_time_sec
            )
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
        """, (
            result_id, correct_easy, wrong_easy,
            correct_medium, wrong_medium,
            correct_hard, wrong_hard, total_time_sec
        ))
        mysql.connection.commit()
        cursor.close()
        return jsonify({'msg': 'Analytics başarıyla kaydedildi'}), 201

    except Exception as e:
        return jsonify({'msg': f'Hata oluştu: {str(e)}'}), 500

@app.route('/api/sync-results', methods=['POST'])
def sync_results():
    data = request.get_json()

    if not data or not isinstance(data, list):
        return jsonify({"msg": "Geçersiz veri formatı"}), 400

    try:
        cursor = mysql.connection.cursor()

        for entry in data:
            user_id = entry.get('user_id')
            score = entry.get('score')
            total = entry.get('totalQuestions')
            completed_at = entry.get('date')  # format: '2025-05-18 20:31:00'

            if not all([user_id, score, total, completed_at]):
                continue

            cursor.execute("""
                INSERT INTO test_results (user_id, score, total, completed_at)
                VALUES (%s, %s, %s, %s)
            """, (user_id, score, total, completed_at))

        mysql.connection.commit()
        cursor.close()
        return jsonify({"msg": "Tüm offline test sonuçları başarıyla senkronize edildi."}), 200

    except Exception as e:
        mysql.connection.rollback()
        return jsonify({'error': str(e)}), 500

@app.route('/api/sync-analytics', methods=['POST'])
def sync_analytics():
    data = request.get_json()

    if not data or not isinstance(data, list):
        return jsonify({"msg": "Geçersiz veri formatı"}), 400

    try:
        cursor = mysql.connection.cursor()

        for analytics in data:
            result_id = analytics.get("result_id")
            correct_easy = analytics.get("correct_easy", 0)
            wrong_easy = analytics.get("wrong_easy", 0)
            correct_medium = analytics.get("correct_medium", 0)
            wrong_medium = analytics.get("wrong_medium", 0)
            correct_hard = analytics.get("correct_hard", 0)
            wrong_hard = analytics.get("wrong_hard", 0)
            total_time_sec = analytics.get("total_time_sec", 0)

            if not result_id:
                continue

            cursor.execute("""
                INSERT INTO test_analytics (
                    result_id, correct_easy, wrong_easy,
                    correct_medium, wrong_medium,
                    correct_hard, wrong_hard, total_time_sec
                ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)
            """, (
                result_id, correct_easy, wrong_easy,
                correct_medium, wrong_medium,
                correct_hard, wrong_hard, total_time_sec
            ))

        mysql.connection.commit()
        cursor.close()
        return jsonify({"msg": "Tüm analytics başarıyla senkronize edildi."}), 200

    except Exception as e:
        mysql.connection.rollback()
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')