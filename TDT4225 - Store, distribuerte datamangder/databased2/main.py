from DbConnector import DbConnector
from tabulate import tabulate
from haversine import haversine


class Queries:

    def __init__(self):
        self.connection = DbConnector()
        self.db_connection = self.connection.db_connection
        self.cursor = self.connection.cursor

    def query1(self):
        query = """
                  SELECT  (
                  SELECT COUNT(*)
                  FROM   User
                  ) AS AmountOfUsers,
                  (
                  SELECT COUNT(*)
                  FROM   Activity
                  ) AS AmountOfActivites,
                  (
                  SELECT COUNT(*)
                  FROM   TrackPoint
                  ) AS AmountOfTrackPoints
                  """

        self.tabulate_rows(
            query=query, table_name="CollectiveAmountOfData")

    def query2(self):
        query = """
                SELECT
                ROUND (
                (SELECT COUNT(*) FROM Activity ) /
                (SELECT COUNT(*) FROM User), 1) AS AvgActivitiesPerUser
                """
        self.tabulate_rows(
            query=query, table_name="Activities / Users")

    def query3(self):
        query = """
                SELECT user_id, COUNT(*) AS ActivitiesPerUser
                FROM Activity
                GROUP BY user_id
                ORDER BY ActivitiesPerUser DESC LIMIT 20
                """

        self.tabulate_rows(
            query=query, table_name="Top20UsersWithMostActivities")

    def query4(self):
        query = """
                SELECT DISTINCT user_id
                FROM Activity
                WHERE transportation_mode = "taxi"
                """

        self.tabulate_rows(
            query=query, table_name="Activity = Taxi")

    def query5(self):
        query = """SELECT transportation_mode, COUNT(*) AS actCount
                    FROM Activity
                    WHERE transportation_mode != 'None'
                    GROUP BY transportation_mode
                    ORDER BY actCount DESC"""

        self.tabulate_rows(
            query=query, table_name="Transportation types and amount "
        )

    def query6(self):
        query_a = """
                  SELECT YEAR(start_date_time) AS year,
                  COUNT(*) as actCount
                  FROM Activity
                  GROUP BY year
                  ORDER BY actCount DESC
                  LIMIT 1
                  """

        self.tabulate_rows(
            query=query_a, table_name="Year with most activities")

        query_b = """
                  SELECT YEAR(start_date_time)
                  AS year,
                  SUM(TIMESTAMPDIFF (HOUR, start_date_time, end_date_time)) AS hours
                  FROM Activity
                  GROUP BY year
                  ORDER BY hours DESC
                  LIMIT 1
                  """

        self.tabulate_rows(
            query=query_b, table_name="Year with most recorded hours")

    def query7(self):
        query = """
                SELECT user_id, lat, lon
                FROM User
                JOIN Activity ON User.id = Activity.user_id
                JOIN TrackPoint ON Activity.id = TrackPoint.activity_id
                WHERE user_id = 112 AND transportation_mode = "walk" AND YEAR(start_date_time) = "2008"
                """

        self.cursor.execute(query)
        query_result = self.cursor.fetchall()

        distance_traveled = 0
        for trackpnt in range(len(query_result)-1):
            start_location = (
                query_result[trackpnt][1], query_result[trackpnt][2])
            end_location = (query_result[trackpnt + 1]
                            [1], query_result[trackpnt + 1][2])
            distance_traveled += haversine(start_location, end_location)
        print("Total distance (in km) walked in 2008, by user = 112:",
              round(distance_traveled, 2))

    def query8(self):
        print("Did not manage to solve this one :(")
        # This one we unfortunately had no clue.

    def query9(self):
        print("Did not manage to solve this one :(")
        # What we tried to do was to somehow to select user_id and a distinct value for activities id.
        # Then we wanted to join TrackPoint and activites, but did not manage to keep track of the same
        # trackpoint file. Hence we could not calculate (would have used TIMESTAMPDIFF) the distance between the trackpoints.

    def query10(self):
        query = """
                SELECT DISTINCT Activity.user_id
                FROM Activity
                JOIN TrackPoint ON Activity.id = TrackPoint.activity_id
                WHERE ROUND(TrackPoint.lat, 3) LIKE 39.916
                AND ROUND(TrackPoint.lon, 3) LIKE 116.397
                """

        self.tabulate_rows(
            query=query, table_name="Year with most recorded hours")

    def query11(self):

        query = """
                SELECT Activity.user_id, Activity.transportation_mode, Count(transportation_mode) AS transAmount
                FROM Activity
                WHERE transportation_mode != 'None'
                GROUP BY Activity.user_id, Activity.transportation_mode
                ORDER BY Activity.user_id, transAmount DESC
                """

        self.cursor.execute(query)
        query_result = self.cursor.fetchall()
        print(query_result)
        transportation_count = {}

        for i in range(len(query_result)):
            key = query_result[i][0]
            if key not in transportation_count:
                transportation_count[key] = query_result[i][1]
            else:
                continue

        list = []
        for k, v in transportation_count.items():
            list.append((k, v))

    def view_top10_data(self):
        all_tables = ["User", "Activity", "TrackPoint"]
        for i in range(3):

            query = f"""SELECT *
                    FROM   {all_tables[i]}
                    LIMIT 10
                    """
            self.tabulate_rows(
                query=query, table_name=all_tables[i] + " Top 10")

    def tabulate_rows(self, query, table_name):
        self.cursor.execute(query)
        rows = self.cursor.fetchall()

        print("Data from table %s, tabulated:" %
              table_name)
        print(tabulate(rows, headers=self.cursor.column_names))
        print()


def main():
    program = None
    try:
        program = Queries()
        # program.query1()
        # program.query2()
        # program.query3()
        # program.query4()
        # program.query5()
        # program.query6()
        # program.query7()
        # program.query8()
        # program.query9()
        # program.query10()
        # program.query11()

        # program.view_top10_data()

    except Exception as e:
        print("ERROR: Failed to use database:", e)
    finally:
        if program:
            program.connection.close_connection()


if __name__ == '__main__':
    main()
