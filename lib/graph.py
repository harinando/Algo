class Graph:

    def __init__(self, words=[]):
        self.E = 0
        self.adj = defaultdict(lambda: [])
        self.v = set()
        for word in words:
            self.v.add(word[0])
            self.v.add(word[-1])
            self.adj[word[0]].append(word[-1])

    def addEdges(self, u, v):
        self.v.add(u)
        self.v.add(v)
        self.adj[u].append(v)

    def toString(self):
        return self.adj
