from DbConnector import DbConnector
from datetime import datetime
import os


class Insert_Data:

    def __init__(self):
        self.connection = DbConnector()
        self.db_connection = self.connection.db_connection
        self.cursor = self.connection.cursor
        self.collection_user = "User"
        self.collection_activity = "Activity"
        self.collection_trackpoint = "TrackPoint"
        self.activity_id = 1
        self.user_id = ""

    # --------------------------------- Reading Helper Functions --------------------------------- #

    def read_root_label_id(self, path_label):
        array = []
        with open(path_label) as f:
            for line in f:
                array.append(line.strip())
        return array

    def read_plt_start_end(self, path_plt):
        with open(path_plt) as file:

            firstLine = file.readlines()[6]
            startTimeSplit = firstLine.split(',')
            startTime = startTimeSplit[-2] + " " + startTimeSplit[-1]
            startTime_object = datetime.fromisoformat(startTime.strip())

            file.seek(0)

            lastLine = file.readlines()[-1]
            endTimeSplit = lastLine.split(',')
            endTime = endTimeSplit[-2] + " " + endTimeSplit[-1]
            endTime_object = datetime.fromisoformat(endTime.strip())
            return startTime_object, endTime_object

    def read_trackpoints(self, path_plt):
        trackpoints = []
        with open(path_plt) as file:
            for tp in file.readlines()[6:]:
                tp_split = tp.split(',')
                trackpoints.append(
                    (self.activity_id, tp_split[0], tp_split[1], tp_split[3], tp_split[5] + " " + tp_split[6].strip()))
        return trackpoints

    def read_label_start_end_transport(self, path_for_label):
        list = []
        with open(path_for_label) as file:
            for line in file.readlines()[1:]:
                split_line = line.strip().split()
                list.append((datetime.fromisoformat(split_line[0].replace(
                    "/", "-") + " " + split_line[1]), datetime.fromisoformat(split_line[2].replace("/", "-") + " " + split_line[3]), split_line[4]))
        return list

    # --------------------------------- Inserting Data --------------------------------- #

    def insert_data(self, path_labels, path_main):
        has_labels = self.read_root_label_id(path_label=path_labels)
        get_users = 0

        # Creating the user collection

        for (root, dirs, files) in os.walk(path_main, topdown=True):
            if (get_users == 0):
                # Inserting Users
                for dir in dirs:
                    is_labeled = False
                    if dir in has_labels:
                        is_labeled = True

                    user_document = {
                        "_id": self.collection_user,
                        "has_label": is_labeled
                        # TODO Insert a list with it's belonging [activity_ids]?
                    }
                    collection = self.db_connection["User"]
                    # Assuming this line underneath inserts a document(row) into the collection (Table)
                    collection.insert_one(user_document)

                    # __OLDWAY__
                    # query_user = "INSERT INTO %s (id, has_labels) VALUES ('%s', %s)"
                    # self.cursor.execute(query_user %
                    #                     (self.collection_user, dir, is_labeled))
                    # self.db_connection.commit()

                get_users += 1

            if root[-14:-11].isnumeric():
                self.user_id = root[-14:-11]

            if "Trajectory" in root:
                for plt in files:
                    path_plt = root + "/" + plt

                    if sum(1 for line in open(path_plt)) > 2506:
                        continue

                    path_start, path_end = self.read_plt_start_end(
                        path_plt=path_plt)

                    if self.user_id not in has_labels:
                        print(
                            "Inserting Activity and TrackPoints for: " + self.user_id)
                        self.insert_activities(
                            None, path_start, path_end)

                        trackpoints = self.read_trackpoints(
                            path_plt=path_plt)

                        self.insert_trackpoints(
                            trackpoints=trackpoints)

                    else:
                        print(
                            "Inserting Activity and TrackPoints with LABELS for: " + self.user_id)
                        all_labels = self.read_label_start_end_transport(
                            path_for_label=root[:-10] + "labels.txt")
                        for label in all_labels:
                            if label[0] == path_start and label[1] == path_end:
                                self.insert_activities(
                                    label[2], path_start, path_end)

                                trackpoints = self.read_trackpoints(
                                    path_plt=path_plt)

                                self.insert_trackpoints(
                                    trackpoints=trackpoints)

    # Inserting Activities
    def insert_activities(self, type_label, start, end):
        # TODO Unsure if this works, but this is the general layout of how it will end up looking.
        print("inserting activities")
        activity_document = {
            # Id is 1, but it's not getting incremented any place yet. Did that in trackpoint earlier.
            "_id": self.activity_id,
            "user_id": self.user_id,
            "transportation_mode": type_label,
            "start_date_time": start,
            "end_date_time": end,
            # TODO list of belonging trackpoint ids as well?
        }
        collection = self.db_connection["Activity"]
        collection.insert_one(activity_document)

        # __OLDWAY__
        # query_activity = "INSERT INTO %s (id, user_id, transportation_mode, start_date_time, end_date_time) VALUES (%s, '%s', '%s', '%s', '%s')"
        # self.cursor.execute(query_activity %
        #                     (self.table_activity, self.activity_id, self.user_id, type_label, start, end))
        # self.db_connection.commit()

    # Inserting batches of TrackPoints by concatination
    def insert_trackpoints(self, trackpoints):
        print("inserting trackpoints")

        # __OLDWAY__
        # self.cursor.execute(
        #     f"INSERT INTO {self.table_trackpoint} (activity_id, lat, lon, altitude, date_time) VALUES {str(trackpoints).strip('[]')}")
        # self.db_connection.commit()

        # self.activity_id += 1


def main():
    program_for_users = None
    try:
        program_for_users = Insert_Data()
        path_to_main = 'dataset/dataset/Data'
        path_to_labels = "dataset/dataset/labeled_ids.txt"
        program_for_users.insert_data(
            path_labels=path_to_labels, path_main=path_to_main)

    except Exception as e:
        print("ERROR: Failed to use database:", e)
    finally:
        if program_for_users:
            program_for_users.connection.close_connection()


if __name__ == '__main__':
    main()
