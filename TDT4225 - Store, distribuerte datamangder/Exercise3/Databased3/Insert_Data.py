from DbConnector import DbConnector
from datetime import datetime
from pprint import pprint
import os


class Insert_Data:
    def __init__(self):
        self.connection = DbConnector()
        self.db_connection = self.connection.db
        self.collection_user = "User"
        self.collection_activity = "Activity"
        self.collection_trackpoint = "TrackPoint"
        self.activity_id = 0
        self.trackpoint_id = 0
        self.user_id = ""
        self.user_list = []
        self.all_tp_id_to_activity = []

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
            startTime = (startTimeSplit[-2] + " " + startTimeSplit[-1]).strip()

            file.seek(0)

            lastLine = file.readlines()[-1]
            endTimeSplit = lastLine.split(',')
            endTime = (endTimeSplit[-2] + " " + endTimeSplit[-1]).strip()
            return startTime, endTime

    def read_trackpoints(self, path_plt):
        trackpoints = []
        with open(path_plt) as file:
            for tp in file.readlines()[6:]:
                tp_split = tp.split(',')
                trackpoint_document = {
                    "_id": self.trackpoint_id,
                    "activity_id": self.activity_id,
                    "lat": float(tp_split[0]),
                    "lon": float(tp_split[1]),
                    "altitude": int(float(tp_split[3])),
                    "date_time": datetime.strptime(tp_split[5] + " " + tp_split[6].strip(), '%Y-%m-%d %H:%M:%S')
                }
                trackpoints.append(trackpoint_document)
                self.all_tp_id_to_activity.append(self.trackpoint_id)
                self.trackpoint_id += 1
        return trackpoints

    def read_label_start_end_transport(self, path_for_label):
        dic = {}
        with open(path_for_label) as file:
            for line in file.readlines()[1:]:
                split_line = line.strip().split()
                dic[split_line[0].replace(
                    "/", "-") + " " + split_line[1]] = [split_line[2].replace("/", "-") + " " + split_line[3], split_line[4]]
        return dic

    def insert_data(self, path_labels, path_main):
        has_labels = self.read_root_label_id(path_label=path_labels)

        inUserDir = True

        for (root, dirs, files) in os.walk(path_main, topdown=True):
            if (inUserDir):
                for dir in dirs:
                    if (dir.isnumeric()):
                        is_labeled = dir in has_labels
                        user_document = {
                            "_id": dir,
                            "has_label": is_labeled
                        }
                        self.user_list.append(user_document)

                inUserDir = False
                collection = self.db_connection[self.collection_user]
                collection.insert_many(self.user_list)

            if root[21:24].isnumeric():
                self.user_id = root[21:24]

            if "Trajectory" in root:
                for plt in files:

                    path_plt = root + "/" + plt
                    if sum(1 for _ in open(path_plt)) > 2506:
                        continue

                    path_start, path_end = self.read_plt_start_end(
                        path_plt=path_plt)

                    if self.user_id not in has_labels:

                        print(
                            "Appending Activity WITHOUT LABELS for: " + self.user_id)

                        all_trackpoints_in_a_file = self.read_trackpoints(
                            path_plt=path_plt)

                        collection = self.db_connection[self.collection_activity]
                        collection.insert_one({
                            "_id": self.activity_id,
                            "user_id": self.user_id,
                            "tp_ids_connected_to_activity": self.all_tp_id_to_activity,
                            "transportation_mode": None,
                            "start_date_time": datetime.strptime(path_start, '%Y-%m-%d %H:%M:%S'),
                            "end_date_time": datetime.strptime(path_end, '%Y-%m-%d %H:%M:%S'),
                        })

                        collection = self.db_connection[self.collection_trackpoint]
                        collection.insert_many(all_trackpoints_in_a_file)
                        self.all_tp_id_to_activity.clear()

                    else:
                        print(
                            "Appending Activity WITH LABELS for: " + self.user_id)
                        all_labels = self.read_label_start_end_transport(
                            path_for_label=root[:-10] + "labels.txt")

                        label = None
                        if path_start in all_labels and all_labels[path_start][0] == path_end:
                            label = all_labels[path_start][1]

                        all_trackpoints_in_a_file = self.read_trackpoints(
                            path_plt=path_plt)

                        collection = self.db_connection[self.collection_activity]
                        collection.insert_one({
                            "_id": self.activity_id,
                            "user_id": self.user_id,
                            "tp_ids_connected_to_activity": self.all_tp_id_to_activity,
                            "transportation_mode": label,
                            "start_date_time": datetime.strptime(path_start, '%Y-%m-%d %H:%M:%S'),
                            "end_date_time": datetime.strptime(path_end, '%Y-%m-%d %H:%M:%S'),
                        })

                        collection = self.db_connection[self.collection_trackpoint]
                        collection.insert_many(all_trackpoints_in_a_file)
                        self.all_tp_id_to_activity.clear()

                    self.activity_id += 1


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
