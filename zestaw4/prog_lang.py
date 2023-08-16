from collections import defaultdict

class ProgLang:

    def __init__(self, filename):
        self.langs_map = defaultdict(list)  # lang->surnames  
        self.progs_map = defaultdict(list)  # surname->langs
        with open(filename, 'r') as f:
            for line in f.readlines():
                lang, *surnames = line.split()
                for surname in surnames:
                    self.progs_map[surname].append(lang)
                    self.langs_map[lang].append(surname)

    def get_langs_map(self):
        return self.langs_map

    def get_progs_map(self):
        return self.progs_map

    def sorted(self, dictionary, function):
        pairs = [(function(key, value), key) for key, value in dictionary.items()]
        pairs.sort()
        return {key:dictionary[key] for _, key in pairs}

    def filtered(self, dictionary, function):
        return {key:value for key, value in dictionary.items() if function(key, value)}

    def get_langs_map_sorted_by_num_of_progs(self):
        return self.sorted(self.langs_map, lambda lang, progs: (-len(progs), lang))

    def get_progs_map_sorted_by_num_of_langs(self):
        return self.sorted(self.progs_map, lambda prog, langs: (-len(langs), prog))

    def get_progs_map_for_num_of_langs_greater_than(self, n):
        return self.filtered(self.progs_map, lambda prog, langs: len(langs) > n)
