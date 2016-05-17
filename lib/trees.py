class Node(object):
    def __init__(self, x, n=0):
        self.key   = x
        self.left  = None
        self.right = None
        self.N = n


class BST:
    def __init__(self):
        self.root = None
        
    def size(self, root):
        if root is None:
            return 0
        return root.N

    """
     PUT:
    """
    def put(self, key):
        if key is None:
            raise "Key can't be None"
        self.root = self._put(self.root, key)
    
    # return node
    def _put(self, root, key):
        if root is None:
            return Node(key, 1)
        if key < root.key:
            root.left = self._put(root.left, key)
        elif key > root.key:
            root.right = self._put(root.right, key)
        
        root.N = 1 + self.size(root.left) + self.size(root.right)
        return root

    """
     FLOOR:
    """
    def floor(self, key):
        if key is None: 
            return None
        node = self._floor(self.root, key)
        return node.key if node is not None else None
    
    def _floor(self, root, key):
        if root is None:
            return None
        
        if root.key == key:
            return root
        
        if root.key > key:
            return self._floor(root.left, key)
        
        temp = self._floor(root.right, key)
        return root if temp is None else temp



    """
    RANK
    """
    def rank(self, key):
        return self._rank(key, self.root)

    def _rank(self, key, root):
        if key == root.key:
            return self.size(root.left)
        elif key < root.key:
            return self._rank(key, root.left)
        else:
            return self._rank(key, root.right) + self.size(root.left) + 1

    """
    SELECT
    """
    def select(self, k):
        return self._select(self.root, k)

    def _select(self, x, k):
        if x is None:
            return
        t = self.size(x.left)
        if t > x:
            return self._select(x.left, k)
        elif t < x:
            return self._select(x.right, k-t-1)
        else:
            return x
    """
    DEPTH
    """
    # recursive
    def depth(self, root):
        if root is None:
            return 0
        return 1 + max(self.depth(root.left), self.depth(root.right))

    # Non-Recursive depth
    def height(self, root):
        q = []
        height = 0
        if root is None:
            return 0

        q.append(root)
        while(True):
            # NodeCount indicates the number of nodes
            # at the current level...
            nodeCount = len(q)

            if nodeCount == 0:
                return height

            height += 1

            # Dequeue all nodes at the current level
            # and enqueue
            while(nodeCount > 0):
                node = q.pop(0)
                if node.left is not None:
                    q.append(node.left)
                if node.right is not None:
                    q.append(node.right)
                nodeCount -= 1


    """
    IsBST:
    """
    def isBST(self):
        return self._isBST(self.root, None, None)

    def _isBST(self, root, _min, _max):

        if root is None:
            return True

        if _min is not None and root.key < _min:
            return False

        if _max is not None and root.key > _max:
            return False

        return self._isBST(root.left, _min, root) and self._isBST(root.right, root, _max)


    """
    INVERT
    """
    def invert(self):
        self._invert(self.root)

    def _invert(self, node):
        if node is None:
            return
        temp = node.left
        node.left = node.right
        node.right = temp
        self._invert(node.left)
        self._invert(node.right)


    def levelOrder(self):
        keys  = []
        queue = []
        queue.append(self.root)
        while(queue):
            x = queue.pop(0)
            if x is None:
                continue
            keys.append(x.key)
            queue.append(x.left)
            queue.append(x.right)
        return keys

    """
    TREE TRAVERSAL:
    """
    def zigzagOrder(self):
        keys = []
        l2r = []
        r2l = []
        r2l.append(self.root)
        while(l2r or r2l):
            while(l2r):
                x = l2r.pop(-1)
                if x is None:
                    continue
                keys.append(x.key)
                r2l.append(x.right)
                r2l.append(x.left)
                
            while(r2l):
                x = r2l.pop(-1)
                if x is None:
                    continue
                keys.append(x.key)
                l2r.append(x.left)
                l2r.append(x.right)
        return keys
    
    # Stack implementation
    def nrinorder(self):
        keys = []
        stack = []
        x = self.root
        while(x or stack):
            if x is not None:
                stack.append(x)
                x = x.left
            else:
                x = stack.pop()
                keys.append(x.key)
                x = x.right
        return keys
    
    # Inorder transversal.
    def inorder(self, node=None):
        keys= []
        node = self.root if node is None else node
        self._inorder(self.root, keys)
        return keys
    
    def _inorder(self, root, keys=[]):
        if root is not None:        
            self._inorder(root.left, keys)
            keys.append(root.key)
            self._inorder(root.right, keys)
    
    # Return boundary nodes.
    def boundary(self):
        keys = set()
        self.left_boundary(self.root, keys)
        self.leaf_boundary(self.root, keys)
        self.right_boundary(self.root, keys)
        return keys
        
    def left_boundary(self, root, keys):
        if root is not None:
            keys.add(root.key)
            self.left_boundary(root.left, keys)
        
    def leaf_boundary(self, root, keys):
        if root is not None:
            if root.left is None and root.right is None:
                keys.add(root.key)
            self.leaf_boundary(root.left, keys)
            self.leaf_boundary(root.right, keys)
            
    def right_boundary(self, root, keys):
        if root is not None:
            keys.add(root.key)
            self.right_boundary(root.right, keys)
            

    """
    Path to Sum
    """
    # Has path that sum to given number from the root to any node.
    def hasPathSum(self, num):
        return self._hasPathSum(self.root, num)
    
    def _hasPathSum(self, root, num):
        if root is None:
            return False
        if num == root.key:
            return True
        return self._hasPathSum(root.left, num - root.key) or self._hasPathSum(root.right, num - root.key)
    
    # Find the sum of all paths
    def findSum(self, num):
        somme = [float('inf')] * self.depth(self.root)
        self._findSum(self.root, num, somme, 0)
        
    def _findSum(self, node, num, paths=[], level=0):
        if node is None:
            return None
        paths[level] = node.key
        somme = 0
        for i in range(level, -1, -1):
            somme += paths[i]
            if somme == num:
                print paths[i:level+1]
        self._findSum(node.left, num, paths, level+1)
        self._findSum(node.right, num, paths, level+1)
        paths[level] = float('inf')